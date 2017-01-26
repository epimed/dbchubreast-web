package dbchubreast_web.entity;
// Generated 26 janv. 2017 10:23:30 by Hibernate Tools 4.3.5.Final

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * AppMenu generated by hbm2java
 */
@Entity
@Table(name = "app_menu", schema = "db_chu_breast")
public class AppMenu implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idMenu;
	private AppMenu rootMenu;
	private String title;
	private String path;
	private Integer level;
	private List<AppMenu> subMenus = new ArrayList<AppMenu>(0);
	private List<AppRole> appRoles = new ArrayList<AppRole>(0);

	public AppMenu() {
	}

	public AppMenu(Integer idMenu, String title, String path, Integer level) {
		this.idMenu = idMenu;
		this.title = title;
		this.path = path;
		this.level = level;
	}

	

	public AppMenu(Integer idMenu, AppMenu rootMenu, String title, String path, Integer level, List<AppMenu> subMenus,
			List<AppRole> appRoles) {
		super();
		this.idMenu = idMenu;
		this.rootMenu = rootMenu;
		this.title = title;
		this.path = path;
		this.level = level;
		this.subMenus = subMenus;
		this.appRoles = appRoles;
	}

	@Id

	@Column(name = "id_menu", unique = true, nullable = false)
	public Integer getIdMenu() {
		return this.idMenu;
	}

	public void setIdMenu(Integer idMenu) {
		this.idMenu = idMenu;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_root_menu")
	public AppMenu getRootMenu() {
		return this.rootMenu;
	}

	public void setRootMenu(AppMenu rootMenu) {
		this.rootMenu = rootMenu;
	}

	@Column(name = "title", nullable = false, length = 500)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "path", nullable = false, length = 500)
	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Column(name = "level", nullable = false)
	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "rootMenu")
	public List<AppMenu> getSubMenus() {
		return this.subMenus;
	}

	public void setSubMenus(List<AppMenu> subMenus) {
		this.subMenus = subMenus;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "appMenus")
	public List<AppRole> getAppRoles() {
		return this.appRoles;
	}

	public void setAppRoles(List<AppRole> appRoles) {
		this.appRoles = appRoles;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idMenu == null) ? 0 : idMenu.hashCode());
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
		AppMenu other = (AppMenu) obj;
		if (idMenu == null) {
			if (other.idMenu != null)
				return false;
		} else if (!idMenu.equals(other.idMenu))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AppMenu [idMenu=" + idMenu + ", rootMenu=" + rootMenu + ", title=" + title + ", path=" + path
				+ ", level=" + level + "]";
	}

	
	
}
