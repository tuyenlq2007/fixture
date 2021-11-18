package org.page.driver;

import org.api.PageApi;
import org.exception.StopTest;

public class PageAction {
	private PageApi typeJs;
	private PageApi typeSlider;
	private PageApi inputIn;
	private PageApi type;
	//android
	private PageApi sendkey;
	private PageApi presskeycode;
	//end android
	private PageApi enter;
	private PageApi open;
	private PageApi close;
	private PageApi click;
	private PageApi clickif;
	private PageApi clickandwait;
	private PageApi clickandwaitloading;
	private PageApi declick;
	private ClickAt clickat;
	private PageApi click_mouseover;
	private PageApi check;
	private PageApi uncheck;
	private PageApi openWindow;
	private PageApi goback;
	private PageApi pause;
	private PageApi runscript;
	private PageApi selectByValue;
	private PageApi selectByText;
	private PageApi chooseByText;
	
	private PageApi getAttribute;
	private PageApi getValue;
	private PageApi getDigit;
	private PageApi getJs;
	private PageApi getid;
	private PageApi getRandom;
	private PageApi submit;
	private PageApi waitforPageToLoad;
	private PageApi waitforCondition;
	private PageApi waitforAsync;
	private PageApi waitForElementIsEnabled;
	private PageApi waitForElement;
	private PageApi switchToWindow;
	private PageApi switchToFrame;
	private PageApi switchToParentFrame;
	private PageApi dragAndDrop;
	private PageApi dragAndDropToObject;
	private PageApi clickjs;
	private PageApi enterjs;
	private PageApi keypress;
	private PageApi keyup;
	private PageApi keydown;
	private PageApi mouseEventHandler;
	private PageApi getRandomNumber;
	private PageApi scrolljs;
	private PageApi setjstext;
	private PageApi selectByIndex;
	private PageApi selectjsByIndex;
	private PageApi selectjsByValue;
	private PageApi inputInAndWaitFor;
	private PageApi iClickAtWithTimeoutPollingEvery;
	private PageApi clickAndWaitFor;
	private PageApi getEvidence;
	private PageApi tryToClickUntilElementIsEnabledWithTimeoutPollingEvery;
	public PageAction() {
		close = new Close();
		goback = new GoBack();
		switchToParentFrame = new SwitchtoParentFrame();
		getEvidence = new GetEvidence();
	}

	public PageAction(String value) {
		open = new Open(value);
		openWindow = new OpenWindow(value);
		click = new Click(value);
		click_mouseover = new ClickMouseover(value);
		declick = new DeClick(value);
		clickat = new ClickAt(value);
		getDigit = new GetDigit(value);
		getJs = new GetJsOutput(value);
		getid = new GetId(value);
		getValue = new GetValue(value);
		getRandom = new GetRandom(value);
		getRandomNumber = new GetRandomNumber(value);
		submit = new Submit(value);
		check = new Check(value);
		uncheck = new UnCheck(value);
		pause = new Pause(value);
		runscript = new RunScript(value);
		waitforPageToLoad = new WaitForPageToLoad(value);

		switchToWindow = new SwitchToWindow(value);
		switchToFrame = new SwitchToFrame(value);

		// Javascript actions
		clickjs = new JsClick(value);
		scrolljs = new ScrollJs(value);
		
	}

	public PageAction(String value1, String value2) {
		clickif = new ClickIf(value1, value2);
		clickandwait = new ClickAndWait(value1, value2);
		clickandwaitloading = new ClickAndWaitForLoading(value1, value2);
		type = new Type(value1, value2);
		typeSlider = new TypeSlider(value1, value2);
		inputIn = new InputIn(value1, value2);
		clickAndWaitFor = new ClickAndWaitFor(value1, value2);
		typeJs = new TypeJs(value1, value2);
		enter = new Enter(value1, value2);
		//Android
		sendkey = new SendKey(value1,value2);
		//endaroid
		getAttribute = new GetAttribute(value1, value2);
		selectByValue = new SelectByValue(value1, value2);
		selectByText = new SelectByText(value1, value2);
		chooseByText = new ChooseByText(value1, value2);
		selectByIndex = new SelectByIndex(value1, value2);
		selectjsByIndex = new SelectjsByIndex(value1, value2);
		selectjsByValue = new SelectjsByValue(value1, value2);

		dragAndDrop = new DragAndDrop(value1, value2);
		dragAndDropToObject = new DragAndDrop(value1, value2);
		keypress = new KEYPRESS(value1, value2);
		keyup = new KEYUP(value1, value2);
		keydown = new KEYDOWN(value1, value2);
		mouseEventHandler = new MouseEventHandler(value1, value2);
		// Javascript actions
		waitforCondition = new WaitForCondition(value1, value2);
		waitforAsync = new WaitForAsync(value1, value2);
		waitForElementIsEnabled= new WaitForElementIsEnabled(value1, value2);
		waitForElement = new WaitForElement(value1, value2);
		
		enterjs = new JsEnter(value1, value2);
		setjstext = new JsSetText(value1, value2);
	}
	
	public PageAction(String value1, String value2, String value3) {
		inputInAndWaitFor = new InputInAndWaitFor(value1, value2, value3);
		iClickAtWithTimeoutPollingEvery = new ClickAtWithTimeoutPollingEvery(value1, value2, value3);
	}
	
	public String getEvidence() throws StopTest {
		// TODO Auto-generated method stub
		return getEvidence.execute();
	}
	
	public PageAction(int value) {
		//Android
		presskeycode = new PressKeyCode(value);
	}
	
	
	public PageAction(String value1, String value2, String value3, String value4) {
		tryToClickUntilElementIsEnabledWithTimeoutPollingEvery = new TryToClickUntilElementIsEnabledWithTimeoutPollingEvery(value1, value2, value3, value4);
	}

	public void check() throws StopTest {
		check.execute();
	}

	public void click() throws StopTest {
		click.execute();
	}

	public void clickandwait() throws StopTest {

		// TODO Auto-generated method stub
		clickandwait.execute();
	}

	public void clickandwaitloading() throws StopTest {

		// TODO Auto-generated method stub
		clickandwaitloading.execute();
	}

	public void clickAt() throws StopTest {
		// TODO Auto-generated method stub
		clickat.execute();
	}

	public void clickMouseover() throws StopTest {
		// TODO Auto-generated method stub
		click_mouseover.execute();
	}
	
	public void clickif() throws StopTest {

		// TODO Auto-generated method stub
		clickif.execute();
	}

	public void clickjs() throws StopTest {
		clickjs.execute();
	}

	public void close() throws StopTest {
		close.execute();
	}

	public void declick() throws StopTest {

		// TODO Auto-generated method stub
		declick.execute();
	}

	public void dragAndDrop() throws StopTest {
		dragAndDrop.execute();
	}

	public void dragAndDropToObject() throws StopTest {
		dragAndDropToObject.execute();
	}

	public void enter() throws StopTest {
		enter.execute();
	}

	public void enterjs() throws StopTest {
		enterjs.execute();
	}

	public String getAttribute() throws StopTest {
		return getAttribute.execute();
	}

	public String getDigit() throws StopTest {
		return getDigit.execute();
	}

	public String getJs() throws StopTest {
		return getJs.execute();
	}

	public String getid() throws StopTest {
		return getid.execute();
	}
	public String getRandom() throws StopTest {
		// TODO Auto-generated method stub
		return getRandom.execute();
	}

	public String getRandomNumber() throws StopTest {
		// TODO Auto-generated method stub
		return getRandomNumber.execute();
	}

	public String getValue() throws StopTest {
		// TODO Auto-generated method stub
		return getValue.execute();
	}

	public void goback() throws StopTest {
		goback.execute();
	}

	public void keydown() throws StopTest {
		// TODO Auto-generated method stub
		keydown.execute();
	}

	public void keypress() throws StopTest {
		// TODO Auto-generated method stub
		keypress.execute();
	}

	public void keyup() throws StopTest {
		// TODO Auto-generated method stub
		keyup.execute();
	}

	public void mouseEventHandler() throws StopTest {
		// TODO Auto-generated method stub
		mouseEventHandler.execute();
	}

	public void open() throws StopTest {
		open.execute();
	}

	public void openWindow() throws StopTest {
		openWindow.execute();
	}

	public void pause() throws StopTest {
		pause.execute();
	}

	public void runscript() throws StopTest {
		runscript.execute();
	}

	public void scrolljs() throws StopTest {
		scrolljs.execute();
	}

	public void selectByIndex() throws StopTest {
		// TODO Auto-generated method stub
		selectByIndex.execute();
	}

	public void selectByText() throws StopTest {
		// TODO Auto-generated method stub
		selectByText.execute();
	}

	public void chooseByText() throws StopTest {
		// TODO Auto-generated method stub
		chooseByText.execute();
	}
	
	
	public void selectByValue() throws StopTest {
		selectByValue.execute();
	}

	public void selectjsByIndex() throws StopTest {

		// TODO Auto-generated method stub
		selectjsByIndex.execute();
	}

	public void selectjsByValue() throws StopTest {

		// TODO Auto-generated method stub
		selectjsByValue.execute();
	}

	public void setjstext() throws StopTest {
		// TODO Auto-generated method stub
		setjstext.execute();
	}

	public void submit() throws StopTest {
		submit.execute();
	}

	public void switchToFrame() throws StopTest {
		switchToFrame.execute();
	}

	public void switchToParentFrame() throws StopTest {

		// TODO Auto-generated method stub
		switchToParentFrame.execute();
	}

	public void switchToWindow() throws StopTest {
		switchToWindow.execute();
	}

	public void type() throws StopTest {
		type.execute();
	}
	
	//android
	public void sendkey() throws StopTest {
		sendkey.execute();
	}
	
	public void presskeycode() throws StopTest {
		presskeycode.execute();
	}
	// end android

	public void typeJs() throws StopTest {

		// TODO Auto-generated method stub
		typeJs.execute();
	}


	public void uncheck() throws StopTest {
		// TODO Auto-generated method stub
		uncheck.execute();
	}

	public void waitforCondition() throws StopTest {
		waitforCondition.execute();
	}

	public void waitForPageLoad() throws StopTest {
		waitforPageToLoad.execute();
	}

	public void waitForElementIsEnabled() throws StopTest {
		// TODO Auto-generated method stub
		waitForElementIsEnabled.execute();
	}

	public void waitForElement() throws StopTest {
		// TODO Auto-generated method stub
		waitForElement.execute();
	}
	
	public void typeSlider() throws StopTest {
		// TODO Auto-generated method stub
		typeSlider.execute();
	}

	
	public void inputIn() throws StopTest {
		// TODO Auto-generated method stub
		inputIn.execute();
	}

	public void waitforAsync() throws StopTest{

		// TODO Auto-generated method stub
		waitforAsync.execute();
	}

	public void inputInAndWaitFor() throws StopTest{
		// TODO Auto-generated method stub
		inputInAndWaitFor.execute();
	}

	public void clickAndWaitFor() throws StopTest{
		// TODO Auto-generated method stub
		clickAndWaitFor.execute();
	}

	public void clickAtWithTimeoutPollingEvery() throws StopTest {
		iClickAtWithTimeoutPollingEvery.execute();	
	}

	public void tryToClickUntilElementIsEnabledWithTimeoutPollingEvery() throws StopTest {
		tryToClickUntilElementIsEnabledWithTimeoutPollingEvery.execute();
		
	}



	

}
