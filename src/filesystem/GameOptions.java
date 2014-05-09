package filesystem;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GameOptions implements Serializable {
	private Integer screenWidth;
	private Integer screenHeight;
	
	public GameOptions(Integer width, Integer height) {
		screenWidth = width;
		screenHeight = height;
	}

	public Integer getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(Integer screenWidth) {
		this.screenWidth = screenWidth;
	}

	public Integer getScreenHeight() {
		return screenHeight;
	}

	public void setScreenHeight(Integer screenHeight) {
		this.screenHeight = screenHeight;
	}
	
	
}
