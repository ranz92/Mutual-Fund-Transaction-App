//Jiayi Zhu
//jiayiz
//08-600
//Dec 10, 2014


package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class UploadFavoriteForm extends FormBean {
	private String url = "";
	private String comment = "";

	public String getUrl() {
		return url;
	}

	public String getComment() {
		return comment;
	}

	public void setUrl(String s) {
		this.url = trimAndConvert(s, "<>\"");
	}

	public void setComment(String s) {
		this.comment = trimAndConvert(s, "<>\"");
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (url == null || url.length() == 0) {
			errors.add("URL is required");
		}
		if (comment == null || comment.length() == 0) {
			errors.add("Comment is required");
		}

		return errors;
	}

}
