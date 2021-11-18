package org.page.direct.verification;

import java.util.List;

import org.api.WebVerificationApi;

public class PageVerification {
	private WebVerificationApi getText;
	private WebVerificationApi getTextOfChildren;
	private WebVerificationApi getDigit;
	private WebVerificationApi getAttribute;
	private WebVerificationApi getSelected;
	private WebVerificationApi getValue;
	private WebVerificationApi getValueByJs;
	private WebVerificationApi getFocusByJs;
	private WebVerificationApi isChecked;
	private WebVerificationApi isVisible;
	private WebVerificationApi getJsOutput;
	private WebVerificationApi getAttributeByJs;
	private WebVerificationApi gettextByJs;

	public PageVerification(String object_repo) {
		getText = new GetText(object_repo);
		getTextOfChildren = new GetTextOfChildren(object_repo);
		getDigit = new GetDigit(object_repo);
		getValue = new GetValue(object_repo);
		getValueByJs = new GetValueByJs(object_repo);
		gettextByJs = new GetTextByJs(object_repo);
		getFocusByJs = new GetFocusByJs(object_repo);
		isChecked = new IsChecked(object_repo);
		isVisible = new IsVisible(object_repo);
		getJsOutput = new GetJsOutput(object_repo);
	}

	public PageVerification(String object_repo, String attrName) {
		getAttribute = new GetAttribute(object_repo, attrName);
		getAttributeByJs = new GetAttributeByJs(object_repo, attrName);

	}

	public PageVerification(String object_repo, String type, String isMultiple) {
		getSelected = new GetSelected(object_repo, type, isMultiple);

	}

	public String getAttribute() throws Exception {
		return getAttribute.execute();
	}

	public String getAttributeByJs() throws Exception {

		// TODO Auto-generated method stub
		return getAttributeByJs.execute();
	}

	public String getDigit() throws Exception {
		return getDigit.execute();
	}

	public String getFocusByJs() throws Exception {
		// TODO Auto-generated method stub
		return getFocusByJs.execute();
	}

	public String getJsOutput() throws Exception {
		// TODO Auto-generated method stub
		return getJsOutput.execute();
	}

	public String getSelected() throws Exception {
		return getSelected.execute();
	}

	public String getText() throws Exception {
		return getText.execute();
	}

	public String getTextByJs() throws Exception {

		// TODO Auto-generated method stub
		return gettextByJs.execute();
	}

	public List<String> getTextOfChildren() throws Exception {
		// TODO Auto-generated method stub
		return getTextOfChildren.executes();
	}

	public String getValue() throws Exception {
		return getValue.execute();
	}

	public String getValueByJs() throws Exception {
		return getValueByJs.execute();
	}

	public String isChecked() throws Exception {
		return isChecked.execute();
	}

	public String isVisible() throws Exception {
		return isVisible.execute();
	}

}
