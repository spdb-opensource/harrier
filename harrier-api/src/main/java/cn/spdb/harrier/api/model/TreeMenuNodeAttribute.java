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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((icon == null) ? 0 : icon.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TreeMenuNodeAttribute other = (TreeMenuNodeAttribute) obj;
		if (icon == null) {
			if (other.icon != null)
				return false;
		} else if (!icon.equals(other.icon))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
    
}
