package chuangbang.entity;

import java.util.Date;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;

public class ChuangUser extends BmobUser {

	private String uAvatar;
	private String uAddress;
	private String uAttribute;// 用户类型
	private Date uBirth;
	private String uDescription;// 用户描述
	private String uFriendCount;// 朋友数
	private String uFollowingCount;//
	private String uFollwerCount;
	private Integer uSex;
	private String uVerified;
	private String uVerifiedReason;
	private String uNickName;
	private String memberType;// 会员类型
	private String uMemberEndTime;// 会员到期时间
	private String uMisstingCount;// 约谈数
	private String workingPosition;
	private String workingCompany;// 公司名

	public String getuAvatar() {
		return uAvatar;
	}

	public void setuAvatar(String uAvatar) {
		this.uAvatar = uAvatar;
	}

	public String getuAddress() {
		return uAddress;
	}

	public void setuAddress(String uAddress) {
		this.uAddress = uAddress;
	}

	public String getuAttribute() {
		return uAttribute;
	}

	public void setuAttribute(String uAttribute) {
		this.uAttribute = uAttribute;
	}

	public Date getuBirth() {
		return uBirth;
	}

	public void setuBirth(Date uBirth) {
		this.uBirth = uBirth;
	}

	public String getuDescription() {
		return uDescription;
	}

	public void setuDescription(String uDescription) {
		this.uDescription = uDescription;
	}

	public String getuFriendCount() {
		return uFriendCount;
	}

	public void setuFriendCount(String uFriendCount) {
		this.uFriendCount = uFriendCount;
	}

	public String getuFollowingCount() {
		return uFollowingCount;
	}

	public void setuFollowingCount(String uFollowingCount) {
		this.uFollowingCount = uFollowingCount;
	}

	public String getuFollwerCount() {
		return uFollwerCount;
	}

	public void setuFollwerCount(String uFollwerCount) {
		this.uFollwerCount = uFollwerCount;
	}

	public String getuVerified() {
		return uVerified;
	}

	public void setuVerified(String uVerified) {
		this.uVerified = uVerified;
	}

	public String getuVerifiedReason() {
		return uVerifiedReason;
	}

	public void setuVerifiedReason(String uVerifiedReason) {
		this.uVerifiedReason = uVerifiedReason;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public String getuMemberEndTime() {
		return uMemberEndTime;
	}

	public void setuMemberEndTime(String uMemberEndTime) {
		this.uMemberEndTime = uMemberEndTime;
	}

	public String getuMisstingCount() {
		return uMisstingCount;
	}

	public void setuMisstingCount(String uMisstingCount) {
		this.uMisstingCount = uMisstingCount;
	}

	public String getuNickName() {
		return uNickName;
	}

	public void setuNickName(String uNickName) {
		this.uNickName = uNickName;
	}

	public Integer getuSex() {
		return uSex;
	}

	public void setuSex(Integer uSex) {
		this.uSex = uSex;
	}

	public String getWorkingPosition() {
		return workingPosition;
	}

	public void setWorkingPosition(String workingPosition) {
		this.workingPosition = workingPosition;
	}

	public String getWorkingCompany() {
		return workingCompany;
	}

	public void setWorkingCompany(String workingCompany) {
		this.workingCompany = workingCompany;
	}

}
