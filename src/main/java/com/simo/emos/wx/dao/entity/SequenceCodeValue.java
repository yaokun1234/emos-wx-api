package com.simo.emos.wx.dao.entity;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SequenceCodeValue {

    @ApiModelProperty(value = "唯一标识", readOnly = true, example = "auto")
    private String id;

    @ApiModelProperty(value = "序列码申请ID", required = false)
    private String applyId;

    @ApiModelProperty(value = "产品id", required = false)
    private String productId;

    @ApiModelProperty(value = "品牌id", required = false)
    private String brandId;

    @ApiModelProperty(value = "产品编码", required = false)
    private String productCode;

    @ApiModelProperty(value = "产品名称", required = false)
    private String productName;

    @ApiModelProperty(value = "包装级别编码", required = false)
    private String packageLeveCode;

    @ApiModelProperty(value = "包装级别名称", required = false)
    private String packageLeveName;

    @ApiModelProperty(value = "物流码序列码", required = false)
    private String value;

    @ApiModelProperty(value = "物流码序列码(加密)", required = false)
    private String encryptValue;

    @ApiModelProperty(value = "营销码序列码值", required = false)
    private String signValue;

    @ApiModelProperty(value = "营销码序列码(加密)", required = false)
    private String encryptSignValue;

    @ApiModelProperty(value = "状态1未激活2已激活3已核销4不存在-1报废", required = false)
    private String status;

    @ApiModelProperty(value = "是否过期", required = false)
    private Boolean isDue;

    @ApiModelProperty(value = "伊利用户ID", required = false)
    private String ylUserId;

    @ApiModelProperty(value = "活动编号", required = false)
    private String activateCode;

    @ApiModelProperty(value = "活动名称", required = false)
    private String activateName;

    @ApiModelProperty(value = "核销省份", required = false)
    private String useProvince;

    @ApiModelProperty(value = "核销城市", required = false)
    private String useCity;

    @ApiModelProperty(value = "手机号码", required = false)
    private String phone;

    @ApiModelProperty(value = "用户实名", required = false)
    private String realName;

    @ApiModelProperty(value = "用户昵称", required = false)
    private String nickName;

    @ApiModelProperty(value = "用户OpenId", required = false)
    private String openId;

    @ApiModelProperty(value = "用户UnionId", required = false)
    private String unionId;

    @ApiModelProperty(value = "是否集团新用户", required = false)
    private Boolean isGroupNewUser;

    @ApiModelProperty(value = "是否品牌新用户", required = false)
    private Boolean isBrandNewUser;

    @ApiModelProperty(value = "是否为真实数据", required = false)
    private Boolean isTrue;

    @ApiModelProperty(value = "经销商编码", required = false)
    private String dealerCode;

    @ApiModelProperty(value = "经销商名称", required = false)
    private String dealerName;


    @ApiModelProperty(value = "是否红包", required = false)
    private Boolean isHongBao;

    @ApiModelProperty(value = "是否测试码", required = false)
    private Boolean isTest;

    @ApiModelProperty(value = "调用方", required = false)
    private String clientId;

    @ApiModelProperty(value = "红包金额", required = false)
    private Long redPackMount;

    @ApiModelProperty(value = "权益类型", required = false)
    //1：红包  2：积分  3：优惠券  4：实物   5.电子卡券
    private Integer equityType;

    @ApiModelProperty(value = "权益值", required = false)
    private String equityValue;




    public static SequenceCodeValue from() {
        SequenceCodeValue sequenceCodeValue = new SequenceCodeValue();
        sequenceCodeValue.setProductCode("null");
        sequenceCodeValue.setProductName("null");
        sequenceCodeValue.setValue("HTTPS://NP.JMHD8.COM/YYSR/");
        sequenceCodeValue.setSignValue("generateSequence.getEncryptValue()");
        sequenceCodeValue.setStatus("generateSequence.getStatus()");
        sequenceCodeValue.setYlUserId("null");
        sequenceCodeValue.setDealerCode("cwsdcvs");
        sequenceCodeValue.setActivateCode("cwdscs");
        sequenceCodeValue.setEncryptValue("dqewdweadca");
        sequenceCodeValue.setEquityType(1);
        sequenceCodeValue.setRealName("fcwec");
        return sequenceCodeValue;
    }



}
