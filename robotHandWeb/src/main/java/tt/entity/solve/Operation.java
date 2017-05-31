package tt.entity.solve;

public class Operation {

	private String type;
	private String name;
	private String url;
	private boolean refresh;

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setRefresh(boolean refresh) {
		this.refresh = refresh;
	}

	public boolean getRefresh() {
		return refresh;
	}

}