package com.page;

public enum EnumColsRentalApplication {
	COL_EMAIL {

		@Override
		public String getString() {

			return "EMAIL";

		}
	},

	COL_STATE {

		@Override
		public String getString() {

			return "STATE";

		}

	};

	public abstract String getString();
}
