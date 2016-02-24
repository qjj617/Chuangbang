package chuangbang.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Project {
	private String proName;
	private String proTel;
	private String proContName;
	private int proTermNumCount;// 团队人数
	private String proLoaction;
	private String proState;// 项目状态
	private String proDescription;// 项目描述
	private String proLogo;
	private String proLink;
	private String userObjeckId;
	private String proField;

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getProTel() {
		return proTel;
	}

	public void setProTel(String proTel) {
		this.proTel = proTel;
	}

	public String getProContName() {
		return proContName;
	}

	public void setProContName(String proContName) {
		this.proContName = proContName;
	}

	public int getProTermNumCount() {
		return proTermNumCount;
	}

	public void setProTermNumCount(int proTermNumCount) {
		this.proTermNumCount = proTermNumCount;
	}

	public String getProLoaction() {
		return proLoaction;
	}

	public void setProLoaction(String proLoaction) {
		this.proLoaction = proLoaction;
	}

	public String getProState() {
		return proState;
	}

	public void setProState(String proState) {
		this.proState = proState;
	}

	public String getProDescription() {
		return proDescription;
	}

	public void setProDescription(String proDescription) {
		this.proDescription = proDescription;
	}

	public String getProLogo() {
		return proLogo;
	}

	public void setProLogo(String proLogo) {
		this.proLogo = proLogo;
	}

	public String getProLink() {
		return proLink;
	}

	public void setProLink(String proLink) {
		this.proLink = proLink;
	}

	public String getUserObjeckId() {
		return userObjeckId;
	}

	public void setUserObjeckId(String userObjeckId) {
		this.userObjeckId = userObjeckId;
	}

	public String getProField() {
		return proField;
	}

	public void setProField(String proField) {
		this.proField = proField;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "项目名" + getProName() + "描述" + getProDescription() + "项目状态"
				+ getProState() + "项目头像：" + getProLogo() + "创始人"
				+ getProContName() + "项目领域" + getProField() + "项目网址"
				+ getProLink() + "项目地域" + getProLoaction();
	}

	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeString(proContName);
		parcel.writeString(proDescription);
		parcel.writeString(proField);
		parcel.writeString(proLink);
		parcel.writeString(proLoaction);
		parcel.writeString(proLogo);
		parcel.writeString(proName);
		parcel.writeString(proState);

	}

	public static final Parcelable.Creator<Project> CREATOR = new Creator<Project>() {
		public Project createFromParcel(Parcel source) {
			Project pro = new Project();
			pro.proContName = source.readString();
			pro.proDescription = source.readString();
			pro.proField = source.readString();
			pro.proLink = source.readString();
			pro.proLoaction = source.readString();
			pro.proLogo = source.readString();
			pro.proName = source.readString();
			pro.proState = source.readString();
			return pro;
		}

		@Override
		public Project[] newArray(int arg0) {
			// TODO Auto-generated method stub
			return new Project[arg0];
		}

	};

}
