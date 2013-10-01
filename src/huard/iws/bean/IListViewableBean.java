package huard.iws.bean;

import java.util.List;

public interface IListViewableBean {

	public List<Field> getFields();

	public class Field {
		private String text;
		private String prefix;
		private String suffix;
		private String align;
		private String width;
		private boolean isEmailAddress;

		public String getWidth() {
			return width;
		}
		public void setWidth(String width) {
			this.width = width;
		}
		public String getAlign() {
			return align;
		}
		public void setAlign(String align) {
			this.align = align;
		}
		public Field(){
			text="";
			prefix="";
			suffix="";
			isEmailAddress=false;
		}
		public void truncate(int num){
			text = text.substring(0, text.length() - num);
		}

		public String getPrefix() {
			return prefix;
		}
		public void setPrefix(String prefix) {
			this.prefix = prefix;
		}
		public String getSuffix() {
			return suffix;
		}
		public void setSuffix(String suffix) {
			this.suffix = suffix;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public boolean getIsEmailAddress() {
			return isEmailAddress;
		}
		public void setIsEmailAddress(boolean isEmail) {
			this.isEmailAddress = isEmail;
		}
	}


}
