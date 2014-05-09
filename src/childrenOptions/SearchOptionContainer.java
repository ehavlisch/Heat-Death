package childrenOptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchOptionContainer implements Serializable {
	private List<SearchOption> options;
	
	public SearchOptionContainer(List<SearchOption> options) {
		this.options = options;
		sortOptions();
	}
	
	private SearchOption getHighest() {
		if(options != null && options.size() > 0) {
			SearchOption highest = options.get(0);
			for(SearchOption option : options) {
				if(option.getRandomThreshold() > highest.getRandomThreshold()) {
					highest = option;
				}
			}
			options.remove(highest);
			return highest;
		} else {
			return null;
		}
	}
	
	private void sortOptions() {
		ArrayList<SearchOption> sortedOptions =  new ArrayList<SearchOption>(options.size());
		while(options != null && options.size() > 0) {
			sortedOptions.add(getHighest());
		}
		this.options = sortedOptions;
	}

	public List<SearchOption> getOptions() {
		return options;
	}

	public void setOptions(List<SearchOption> options) {
		this.options = options;
	}
}
