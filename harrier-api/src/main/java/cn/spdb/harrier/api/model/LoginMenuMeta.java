package cn.spdb.harrier.api.model;



public class LoginMenuMeta{
	
	private boolean hideInMenu;
	private String title;
	private boolean notCache;
	private String icon;
	public boolean isHideInMenu() {
		return hideInMenu;
	}
	public void setHideInMenu(boolean hideInMenu) {
		this.hideInMenu = hideInMenu;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean isNotCache() {
		return notCache;
	}
	public void setNotCache(boolean notCache) {
		this.notCache = notCache;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (hideInMenu ? 1231 : 1237);
		result = prime * result + ((icon == null) ? 0 : icon.hashCode());
		result = prime * result + (notCache ? 1231 : 1237);
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		LoginMenuMeta other = (LoginMenuMeta) obj;
		if (hideInMenu != other.hideInMenu)
			return false;
		if (icon == null) {
			if (other.icon != null)
				return false;
		} else if (!icon.equals(other.icon))
			return false;
		if (notCache != other.notCache)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	
}
