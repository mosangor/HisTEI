package info.histei.lists;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Created by mike on 3/4/14.
 */
public class ListItemAdapter implements ListItem {

    protected static final String VALUE_ATTRIB_NAME = "value";
    protected static final String LABEL_ELEMENT_NAME = "label";
    protected static final String TOOLTIP_ELEMENT_NAME = "tooltip";

    protected String value;
    protected String label;
    protected String tooltip;

    public ListItemAdapter(String value, String label, String tooltip) {
        this.value = StringUtils.trimToEmpty(value);
        this.label = StringUtils.trimToEmpty(label);
        this.tooltip = StringUtils.trimToEmpty(tooltip);
    }

    @NotNull
    @Override
    public String getValue() {
        return value;
    }

    @NotNull
    @Override
    public String getLabel() {
        return label;
    }

    @NotNull
    @Override
    public String getTooltip() {
        return tooltip;
    }

    @Override
    public String toString() {
        return getLabel();
    }
}
