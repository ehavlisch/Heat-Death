package gameOptions;

public enum Resolution {
	VGA(800, 600), iPhone5Retina(1136, 640), HD1080i(1440, 1080), HD1080(1920, 1080);
	
	private int width;
	private int height;
	
	Resolution(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public String toString() {
		return name() + " (" + width + ", " + height + ")";
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	
}
