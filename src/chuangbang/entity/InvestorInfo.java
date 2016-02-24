package chuangbang.entity;

import cn.bmob.v3.BmobObject;

public class InvestorInfo extends BmobObject {
	private String objectId;
	private String investmentField;// 投资领域
	private String investmentStage;// 投资阶段
	private String investmentCase;// 投资案例
	private String feedbackCount;// 反馈数
	private String projectGetCount;// 收获项目数

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getInvestmentField() {
		return investmentField;
	}

	public void setInvestmentField(String investmentField) {
		this.investmentField = investmentField;
	}

	public String getInvestmentStage() {
		return investmentStage;
	}

	public void setInvestmentStage(String investmentStage) {
		this.investmentStage = investmentStage;
	}

	public String getInvestmentCase() {
		return investmentCase;
	}

	public void setInvestmentCase(String investmentCase) {
		this.investmentCase = investmentCase;
	}

	public String getFeedbackCount() {
		return feedbackCount;
	}

	public void setFeedbackCount(String feedbackCount) {
		this.feedbackCount = feedbackCount;
	}

	public String getProjectGetCount() {
		return projectGetCount;
	}

	public void setProjectGetCount(String projectGetCount) {
		this.projectGetCount = projectGetCount;
	}

}
