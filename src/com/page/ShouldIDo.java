package com.page;

import java.util.List;

public class ShouldIDo {
	  private String expect;
	  private String actual;
	  private String argument;

	  public void setExpect(String expect) {
	    this.expect = expect;
	  }

	  public void setActual(String actual) {
		    this.actual = actual;
	  }

	  public void setArgument(String argument) {
		    this.argument = argument;
	  }
	  
	  public void setToDo(String method) {
		  
		  //return (actual.equals(expect) && abc.equals("123")) ? "yes" : "no";
	  }

	  	public String result() {
		  
		  return (actual.equals(expect) ) ? "yes" : "no";
	  }

	  // The following functions are optional.  If they aren't declared they'll be ignored.
	  public void execute() {
	  }

	  public void reset() {
	  }

	  public void table(List<List<String>> table) {
	  }

	  public void beginTable() {
	  }

	  public void endTable() {
	  }
}
