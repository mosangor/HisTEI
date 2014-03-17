package eu.emergingstandards.lists.styled;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import eu.emergingstandards.events.EMSTRefreshEventListener;
import eu.emergingstandards.lists.EMSTListItem;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ro.sync.ecss.extensions.api.node.AuthorElement;
import ro.sync.exml.workspace.api.editor.page.author.WSAuthorEditorPage;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static eu.emergingstandards.utils.EMSTOxygenUtils.escapeCommas;
import static eu.emergingstandards.utils.EMSTOxygenUtils.refreshNode;

/**
 * Created by mike on 3/4/14.
 */
public abstract class EMSTAbstractStyledList<I extends EMSTListItem>
        implements EMSTStyledList, EMSTRefreshEventListener {

    private final WeakReference<AuthorElement> authorElement;
    private WeakReference<WSAuthorEditorPage> authorPage;

    private String values = "";
    private String labels = "";
    private String tooltips = "";

    protected EMSTAbstractStyledList(AuthorElement authorElement) {
        this.authorElement = new WeakReference<>(authorElement);
    }

    @Nullable
    @Override
    public final AuthorElement getAuthorElement() {
        return authorElement.get();
    }

    @Nullable
    @Override
    public final WSAuthorEditorPage getAuthorPage() {
        if (authorPage != null) {
            return authorPage.get();
        } else {
            return null;
        }
    }

    @Override
    public final synchronized void setAuthorPage(WSAuthorEditorPage newAuthorPage) {
        if (newAuthorPage != null) {
            authorPage = new WeakReference<>(newAuthorPage);
        } else {
            authorPage = null;
        }
    }

    @NotNull
    @Override
    public abstract List<I> getItems();

    @NotNull
    @Override
    public abstract String getEditPropertyQualified();

    @NotNull
    @Override
    public String getOxygenValues() {
        if (values.isEmpty()) {
            List<String> vals = transform(
                    new Function<I, String>() {
                        @Override
                        public String apply(I item) {
                            return item.getValue();
                        }
                    }
            );
            values = StringUtils.join(vals, ",");
        }
        return values;
    }

    @NotNull
    @Override
    public String getOxygenLabels() {
        if (labels.isEmpty()) {
            List<String> labs = transform(
                    new Function<I, String>() {
                        @Override
                        public String apply(I item) {
                            return item.getLabel();
                        }
                    }
            );
            labels = StringUtils.join(escapeCommas(labs), ",");
        }
        return labels;
    }

    @NotNull
    @Override
    public String getOxygenTooltips() {
        if (tooltips.isEmpty()) {
            List<String> tips = transform(
                    new Function<I, String>() {
                        @Override
                        public String apply(I item) {
                            return item.getTooltip();
                        }
                    }
            );
            tooltips = StringUtils.join(escapeCommas(tips), ",");
        }
        return tooltips;
    }

    @Override
    public void reset() {
        values = "";
        labels = "";
        tooltips = "";
    }

    //    EMSTRefreshEventListener Method

    @Override
    public void refresh() {
        reset();

        refreshNode(getAuthorPage(), getAuthorElement());
    }

    private synchronized List<String> transform(Function<I, String> function) {
        return new ArrayList<>(Lists.transform(getItems(), function));
    }

}
