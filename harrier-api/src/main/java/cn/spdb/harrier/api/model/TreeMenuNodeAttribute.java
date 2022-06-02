package cn.spdb.harrier.api.model;

/**
 * 菜单树节点属性.
 *
 */
public class TreeMenuNodeAttribute {
    /** 图标. */
    private String icon;
    /** url地址. */
    private String url;

    public String getIcon() {
        return icon;
    }

    public String getUrl() {
        return url;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
