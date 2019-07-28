package io.github.crashgamescrmc.StockMarket;

public class Utils {

	public static boolean isSmaller(String version, long build, String ref, long ref_build) {

		String[] versionArray = version.split(".");
		String[] refArray = ref.split(".");

		for (int i = 0; i < versionArray.length; i++) {
			if (Integer.parseInt(versionArray[i]) > Integer.parseInt(refArray[i])) {
				return false;
			} else if (Integer.parseInt(versionArray[i]) < Integer.parseInt(refArray[i])) {
				return true;
			}
		}
		return false;
	}

}
