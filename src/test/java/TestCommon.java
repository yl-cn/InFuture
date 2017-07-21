import com.future.convertor.*;
import com.future.data.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

@Slf4j
public class TestCommon {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Entity1 {
        private Date updateDate;
        private BigDecimal amount;
        private Byte flag;
        private String comment;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public class Entity2 {
        //@Builder.Default
        private String updateDate ="";
        //@Builder.Default
        private String amount = "";
        private String flag;
        private String comment;
    }

    public class Entity3 {
        private String fld1;

        private String fld2;

        public Entity3() {

        }

        public String getFld1() {
            return fld1;
        }

        public void setFld1(String fld1) {
            this.fld1 = fld1;
        }

        public String getFld2() {
            return fld2;
        }

        public void setFld2(String fld2) {
            this.fld2 = fld2;
        }
    }

    @Test
    public void testCopyProperties() throws  Exception{
        Entity1 entity1 = new Entity1();
        entity1.setAmount(null);
        entity1.setUpdateDate(new Date());

        Entity2 entity2 = new Entity2();

        //BeanUtils.copyProperties(entity2, entity1);

        //log.info("result: {}  & {}", entity2.getUpdateDate(), entity2.getAmount());

        BeanUtilsBean beanUtilsBean = new BeanUtilsBean();

//如果没有下面几行，则在转换null时会抛异常，例如：org.apache.commons.beanutils.ConversionException: No value specified for 'BigDecimal'
//在org.apache.commons.beanutils.converters这个包下面有很多的Converter，可以按需要使用
        beanUtilsBean.getConvertUtils().register(new org.apache.commons.beanutils.converters.BigDecimalConverter(null), BigDecimal.class);

        DateConverterEx dateConverterEx = new DateConverterEx(null);
        //dateConverterEx.setReturnEmptyForNull(true);
        dateConverterEx.setPattern("yyyyMMddHHmmss");

        beanUtilsBean.getConvertUtils().register(dateConverterEx, java.util.Date.class);
        beanUtilsBean.getConvertUtils().register(new StringConverterEx(), java.lang.String.class);

        //beanUtilsBean.getConvertUtils().register(new org.apache.commons.beanutils.converters.SqlTimestampConverter(null), java.sql.Timestamp.class);
        beanUtilsBean.getConvertUtils().register(new org.apache.commons.beanutils.converters.SqlDateConverter(null), java.sql.Date.class);
        //beanUtilsBean.getConvertUtils().register(new org.apache.commons.beanutils.converters.SqlTimeConverter(null), java.sql.Time.class);

        //beanUtilsBean.copyProperties(entity2, entity1);

        BeanConvertor.copyProperties(entity2, entity1);

        log.info("result2: {}  & {}", entity2.getUpdateDate(), entity2.getAmount());

        //new BeanUtilEmptyForNull().copyProperties(entity2, entity1);


        //log.info("result3: {}  & {}", entity2.getUpdateDate(), entity2.getAmount());
    }

    @Test
    public void testCopyProperties2() throws  Exception {
        Entity1 entity1 = new Entity1();
        entity1.setAmount(null);
        entity1.setUpdateDate(new Date());

        Entity2 entity2 = new Entity2();
        entity2.setUpdateDate("2017-01-01 00:00:00");
        entity2.setAmount("33.333");

        BeanUtilsEx.copyProperties(entity2, entity1);
        log.info("result3: {}  & {}  & {}", entity2.getUpdateDate(), entity2.getAmount(), entity2.getFlag());
    }

    @Test
    public void testTrimStrFld() throws Exception {
        Entity2 entity2 = new Entity2();
        entity2.setComment("  x  ");

        Entity2 entity1 = new Entity2();

        ConvertorUtil.trimBeanStringField(entity2, entity1);

        Assert.assertEquals("x", entity1.getComment());

/*
        Entity3 entity3 = new Entity3();
        entity3.setFld1("    xxx    ");
        entity3.setFld2("x  y x  ");
        Entity3 entity4 = ConvertorUtil.trimBeanStringField(entity3);

        System.out.println(entity4.getFld1());*/

    }

    @Test
    public void testClassInstance() throws Exception{
        User e3 = User.class.newInstance();
        e3.setName(" xxx ");

        System.out.println("#" + e3.getName() + "#");

        User e4 = ConvertorUtil.trimBeanStringField(e3);
        System.out.println("#" + e4.getName() + "#");

    }
}
