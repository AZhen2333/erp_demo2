package cn.redsun.bos.entity;
/**
 * 菜单实体类
 * @author Administrator *
 */
public class Menu {	
	private String menuid;//菜单ID
	private String menuname;//菜单名称
	private String icon;//图标
	private String url;//URL
	private String pid;//上级菜单ID

	public String getMenuid() {		
		return menuid;
	}
	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}
	public String getMenuname() {		
		return menuname;
	}
	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}
	public String getIcon() {		
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getUrl() {		
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPid() {		
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}

}
