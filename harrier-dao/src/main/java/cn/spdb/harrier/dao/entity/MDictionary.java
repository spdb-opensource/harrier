package cn.spdb.harrier.dao.entity;

import javax.annotation.Generated;

public class MDictionary {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-12T15:33:57.684+08:00", comments="Source field: m_dictionary.id")
    private Long id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-12T15:33:57.691+08:00", comments="Source field: m_dictionary.dic_code")
    private String dicCode;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-12T15:33:57.691+08:00", comments="Source field: m_dictionary.dic_key")
    private String dicKey;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-12T15:33:57.691+08:00", comments="Source field: m_dictionary.dic_name")
    private String dicName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-12T15:33:57.691+08:00", comments="Source field: m_dictionary.dic_value")
    private String dicValue;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-12T15:33:57.692+08:00", comments="Source field: m_dictionary.dic_desc")
    private String dicDesc;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-12T15:33:57.69+08:00", comments="Source field: m_dictionary.id")
    public Long getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-12T15:33:57.69+08:00", comments="Source field: m_dictionary.id")
    public void setId(Long id) {
        this.id = id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-12T15:33:57.691+08:00", comments="Source field: m_dictionary.dic_code")
    public String getDicCode() {
        return dicCode;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-12T15:33:57.691+08:00", comments="Source field: m_dictionary.dic_code")
    public void setDicCode(String dicCode) {
        this.dicCode = dicCode == null ? null : dicCode.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-12T15:33:57.691+08:00", comments="Source field: m_dictionary.dic_key")
    public String getDicKey() {
        return dicKey;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-12T15:33:57.691+08:00", comments="Source field: m_dictionary.dic_key")
    public void setDicKey(String dicKey) {
        this.dicKey = dicKey == null ? null : dicKey.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-12T15:33:57.691+08:00", comments="Source field: m_dictionary.dic_name")
    public String getDicName() {
        return dicName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-12T15:33:57.691+08:00", comments="Source field: m_dictionary.dic_name")
    public void setDicName(String dicName) {
        this.dicName = dicName == null ? null : dicName.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-12T15:33:57.691+08:00", comments="Source field: m_dictionary.dic_value")
    public String getDicValue() {
        return dicValue;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-12T15:33:57.692+08:00", comments="Source field: m_dictionary.dic_value")
    public void setDicValue(String dicValue) {
        this.dicValue = dicValue == null ? null : dicValue.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-12T15:33:57.692+08:00", comments="Source field: m_dictionary.dic_desc")
    public String getDicDesc() {
        return dicDesc;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-12T15:33:57.692+08:00", comments="Source field: m_dictionary.dic_desc")
    public void setDicDesc(String dicDesc) {
        this.dicDesc = dicDesc == null ? null : dicDesc.trim();
    }
}