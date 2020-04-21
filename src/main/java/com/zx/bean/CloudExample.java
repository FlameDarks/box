package com.zx.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CloudExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table cloud
     *
     * @mbg.generated Thu Apr 09 15:15:40 HKT 2020
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table cloud
     *
     * @mbg.generated Thu Apr 09 15:15:40 HKT 2020
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table cloud
     *
     * @mbg.generated Thu Apr 09 15:15:40 HKT 2020
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cloud
     *
     * @mbg.generated Thu Apr 09 15:15:40 HKT 2020
     */
    public CloudExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cloud
     *
     * @mbg.generated Thu Apr 09 15:15:40 HKT 2020
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cloud
     *
     * @mbg.generated Thu Apr 09 15:15:40 HKT 2020
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cloud
     *
     * @mbg.generated Thu Apr 09 15:15:40 HKT 2020
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cloud
     *
     * @mbg.generated Thu Apr 09 15:15:40 HKT 2020
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cloud
     *
     * @mbg.generated Thu Apr 09 15:15:40 HKT 2020
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cloud
     *
     * @mbg.generated Thu Apr 09 15:15:40 HKT 2020
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cloud
     *
     * @mbg.generated Thu Apr 09 15:15:40 HKT 2020
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cloud
     *
     * @mbg.generated Thu Apr 09 15:15:40 HKT 2020
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cloud
     *
     * @mbg.generated Thu Apr 09 15:15:40 HKT 2020
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cloud
     *
     * @mbg.generated Thu Apr 09 15:15:40 HKT 2020
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table cloud
     *
     * @mbg.generated Thu Apr 09 15:15:40 HKT 2020
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andCloudIdIsNull() {
            addCriterion("cloud_id is null");
            return (Criteria) this;
        }

        public Criteria andCloudIdIsNotNull() {
            addCriterion("cloud_id is not null");
            return (Criteria) this;
        }

        public Criteria andCloudIdEqualTo(Integer value) {
            addCriterion("cloud_id =", value, "cloudId");
            return (Criteria) this;
        }

        public Criteria andCloudIdNotEqualTo(Integer value) {
            addCriterion("cloud_id <>", value, "cloudId");
            return (Criteria) this;
        }

        public Criteria andCloudIdGreaterThan(Integer value) {
            addCriterion("cloud_id >", value, "cloudId");
            return (Criteria) this;
        }

        public Criteria andCloudIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("cloud_id >=", value, "cloudId");
            return (Criteria) this;
        }

        public Criteria andCloudIdLessThan(Integer value) {
            addCriterion("cloud_id <", value, "cloudId");
            return (Criteria) this;
        }

        public Criteria andCloudIdLessThanOrEqualTo(Integer value) {
            addCriterion("cloud_id <=", value, "cloudId");
            return (Criteria) this;
        }

        public Criteria andCloudIdIn(List<Integer> values) {
            addCriterion("cloud_id in", values, "cloudId");
            return (Criteria) this;
        }

        public Criteria andCloudIdNotIn(List<Integer> values) {
            addCriterion("cloud_id not in", values, "cloudId");
            return (Criteria) this;
        }

        public Criteria andCloudIdBetween(Integer value1, Integer value2) {
            addCriterion("cloud_id between", value1, value2, "cloudId");
            return (Criteria) this;
        }

        public Criteria andCloudIdNotBetween(Integer value1, Integer value2) {
            addCriterion("cloud_id not between", value1, value2, "cloudId");
            return (Criteria) this;
        }

        public Criteria andCloudNameIsNull() {
            addCriterion("cloud_name is null");
            return (Criteria) this;
        }

        public Criteria andCloudNameIsNotNull() {
            addCriterion("cloud_name is not null");
            return (Criteria) this;
        }

        public Criteria andCloudNameEqualTo(String value) {
            addCriterion("cloud_name =", value, "cloudName");
            return (Criteria) this;
        }

        public Criteria andCloudNameNotEqualTo(String value) {
            addCriterion("cloud_name <>", value, "cloudName");
            return (Criteria) this;
        }

        public Criteria andCloudNameGreaterThan(String value) {
            addCriterion("cloud_name >", value, "cloudName");
            return (Criteria) this;
        }

        public Criteria andCloudNameGreaterThanOrEqualTo(String value) {
            addCriterion("cloud_name >=", value, "cloudName");
            return (Criteria) this;
        }

        public Criteria andCloudNameLessThan(String value) {
            addCriterion("cloud_name <", value, "cloudName");
            return (Criteria) this;
        }

        public Criteria andCloudNameLessThanOrEqualTo(String value) {
            addCriterion("cloud_name <=", value, "cloudName");
            return (Criteria) this;
        }

        public Criteria andCloudNameLike(String value) {
            addCriterion("cloud_name like", value, "cloudName");
            return (Criteria) this;
        }

        public Criteria andCloudNameNotLike(String value) {
            addCriterion("cloud_name not like", value, "cloudName");
            return (Criteria) this;
        }

        public Criteria andCloudNameIn(List<String> values) {
            addCriterion("cloud_name in", values, "cloudName");
            return (Criteria) this;
        }

        public Criteria andCloudNameNotIn(List<String> values) {
            addCriterion("cloud_name not in", values, "cloudName");
            return (Criteria) this;
        }

        public Criteria andCloudNameBetween(String value1, String value2) {
            addCriterion("cloud_name between", value1, value2, "cloudName");
            return (Criteria) this;
        }

        public Criteria andCloudNameNotBetween(String value1, String value2) {
            addCriterion("cloud_name not between", value1, value2, "cloudName");
            return (Criteria) this;
        }

        public Criteria andCloudUrlIsNull() {
            addCriterion("cloud_url is null");
            return (Criteria) this;
        }

        public Criteria andCloudUrlIsNotNull() {
            addCriterion("cloud_url is not null");
            return (Criteria) this;
        }

        public Criteria andCloudUrlEqualTo(String value) {
            addCriterion("cloud_url =", value, "cloudUrl");
            return (Criteria) this;
        }

        public Criteria andCloudUrlNotEqualTo(String value) {
            addCriterion("cloud_url <>", value, "cloudUrl");
            return (Criteria) this;
        }

        public Criteria andCloudUrlGreaterThan(String value) {
            addCriterion("cloud_url >", value, "cloudUrl");
            return (Criteria) this;
        }

        public Criteria andCloudUrlGreaterThanOrEqualTo(String value) {
            addCriterion("cloud_url >=", value, "cloudUrl");
            return (Criteria) this;
        }

        public Criteria andCloudUrlLessThan(String value) {
            addCriterion("cloud_url <", value, "cloudUrl");
            return (Criteria) this;
        }

        public Criteria andCloudUrlLessThanOrEqualTo(String value) {
            addCriterion("cloud_url <=", value, "cloudUrl");
            return (Criteria) this;
        }

        public Criteria andCloudUrlLike(String value) {
            addCriterion("cloud_url like", value, "cloudUrl");
            return (Criteria) this;
        }

        public Criteria andCloudUrlNotLike(String value) {
            addCriterion("cloud_url not like", value, "cloudUrl");
            return (Criteria) this;
        }

        public Criteria andCloudUrlIn(List<String> values) {
            addCriterion("cloud_url in", values, "cloudUrl");
            return (Criteria) this;
        }

        public Criteria andCloudUrlNotIn(List<String> values) {
            addCriterion("cloud_url not in", values, "cloudUrl");
            return (Criteria) this;
        }

        public Criteria andCloudUrlBetween(String value1, String value2) {
            addCriterion("cloud_url between", value1, value2, "cloudUrl");
            return (Criteria) this;
        }

        public Criteria andCloudUrlNotBetween(String value1, String value2) {
            addCriterion("cloud_url not between", value1, value2, "cloudUrl");
            return (Criteria) this;
        }

        public Criteria andCloudTimeIsNull() {
            addCriterion("cloud_time is null");
            return (Criteria) this;
        }

        public Criteria andCloudTimeIsNotNull() {
            addCriterion("cloud_time is not null");
            return (Criteria) this;
        }

        public Criteria andCloudTimeEqualTo(Date value) {
            addCriterion("cloud_time =", value, "cloudTime");
            return (Criteria) this;
        }

        public Criteria andCloudTimeNotEqualTo(Date value) {
            addCriterion("cloud_time <>", value, "cloudTime");
            return (Criteria) this;
        }

        public Criteria andCloudTimeGreaterThan(Date value) {
            addCriterion("cloud_time >", value, "cloudTime");
            return (Criteria) this;
        }

        public Criteria andCloudTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("cloud_time >=", value, "cloudTime");
            return (Criteria) this;
        }

        public Criteria andCloudTimeLessThan(Date value) {
            addCriterion("cloud_time <", value, "cloudTime");
            return (Criteria) this;
        }

        public Criteria andCloudTimeLessThanOrEqualTo(Date value) {
            addCriterion("cloud_time <=", value, "cloudTime");
            return (Criteria) this;
        }

        public Criteria andCloudTimeIn(List<Date> values) {
            addCriterion("cloud_time in", values, "cloudTime");
            return (Criteria) this;
        }

        public Criteria andCloudTimeNotIn(List<Date> values) {
            addCriterion("cloud_time not in", values, "cloudTime");
            return (Criteria) this;
        }

        public Criteria andCloudTimeBetween(Date value1, Date value2) {
            addCriterion("cloud_time between", value1, value2, "cloudTime");
            return (Criteria) this;
        }

        public Criteria andCloudTimeNotBetween(Date value1, Date value2) {
            addCriterion("cloud_time not between", value1, value2, "cloudTime");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table cloud
     *
     * @mbg.generated do_not_delete_during_merge Thu Apr 09 15:15:40 HKT 2020
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table cloud
     *
     * @mbg.generated Thu Apr 09 15:15:40 HKT 2020
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}