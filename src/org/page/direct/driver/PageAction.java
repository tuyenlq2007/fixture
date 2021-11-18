package org.page.direct.driver;

import org.api.WebApi;
import org.exception.StopTest;

public class PageAction {
	private WebApi typeJs;
	private WebApi typeSlider;
	private WebApi inputIn;
	private WebApi type;
	//android
	private WebApi sendkey;
	private WebApi presskeycode;
	//end android
	private WebApi enter;
	private WebApi open;
	private WebApi close;
	private WebApi click;
	private WebApi clickif;
	private WebApi clickandwait;
	private WebApi clickandwaitloading;
	private WebApi declick;
	private WebApi clickat;
	private WebApi click_mouseover;
	private WebApi check;
	private WebApi uncheck;
	private WebApi openWindow;
	private WebApi goback;
	private WebApi pause;
	private WebApi runscript;
	private WebApi selectByValue;
	private WebApi selectByText;
	private WebApi chooseByText;
	
	private WebApi mousedown;
	private WebApi mouseup;
	private WebApi mousemove;
	private WebApi getAttribute;
	private WebApi getValue;
	private WebApi getText;
	private WebApi getDigit;
	private WebApi getJs;
	private WebApi getid;
	private WebApi getRandom;
	private WebApi submit;
	private WebApi waitforPageToLoad;
	private WebApi waitforCondition;
	private WebApi waitforAsync;
	private WebApi waitForElementIsEnabled;
	private WebApi waitForElement;
	private WebApi switchToWindow;
	private WebApi switchToFrame;
	private WebApi switchToParentFrame;
	private WebApi dragAndDrop;
	private WebApi dragAndDropToObject;
	private WebApi clickjs;
	private WebApi enterjs;
	private WebApi keypress;
	private WebApi keyup;
	private WebApi keydown;
	private WebApi mouseEventHandler;
	private WebApi getRandomNumber;
	private WebApi scrolljs;
	private WebApi setjstext;
	private WebApi selectByIndex;
	private WebApi selectjsByIndex;
	private WebApi selectjsByValue;
	private WebApi inputInAndWaitFor;
	private WebApi iClickAtWithTimeoutPollingEvery;
	private WebApi clickAndWaitFor;
	private WebApi getEvidence;
	private WebApi tryToClickUntilElementIsEnabledWithTimeoutPollingEvery;
	
	public PageAction() {
		close = new Close();
		goback = new GoBack();
		switchToParentFrame = new SwitchtoParentFrame();
		getEvidence = new GetEvidence();
	}

	public PageAction(String value) {
		mousedown = new MouseDown(value);
		mouseup = new MouseDown(value);
		mousemove = new MouseDown(value);
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
		getText = new GetText(value);
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
	

	public void mousedown() throws StopTest{
		 mousedown.execute();
	}

	public void mousemove() throws StopTest{
		 mousemove.execute();
	}

	public void mouseup() throws StopTest{
		 mouseup.execute();
	}

	public void click() throws StopTest {
		click.execute();
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

	public void type() throws StopTest {
		type.execute();
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


	public String getText() throws StopTest {
		// TODO Auto-generated method stub
		return getText.execute();
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
