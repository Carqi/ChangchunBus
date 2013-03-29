package com.carqi.ccbus.test;



import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import android.test.AndroidTestCase;
import android.util.Log;

public class PinyinTest extends AndroidTestCase {
	private static final String TAG = "PinyinTest";
	
    /**   
    * 汉字转换位汉语拼音首字母，英文字符不变   
    * @param chines 汉字   
    * @return 拼音   
    */    
    public String converterToFirstSpell(String chines){            
        String pinyinName = "";     
        char[] nameChar = chines.toCharArray();     
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();     
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);     
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);     
        for (int i = 0; i < nameChar.length; i++) { 
        	//判断字符是否是汉字
            if (String.valueOf(nameChar[i]).matches("[\u4e00-\u9fa5]")) {     
                try {    
                	if(String.valueOf(nameChar[i]).equals("长")){
                		pinyinName += PinyinHelper.toHanyuPinyinStringArray('常', defaultFormat)[0].charAt(0);                                
                	}else{
                	    pinyinName += PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0].charAt(0);     
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {     
                    e.printStackTrace();     
                }     
            }else{     
                pinyinName += nameChar[i];     
            }     
        }     
        return pinyinName;     
    } 
	
    /**   
    * 汉字转换位汉语拼音，英文字符不变   
    * @param chines 汉字   
    * @return 拼音   
    */    
    public String converterToSpell(String chines){             
        String pinyinName = "";     
        char[] nameChar = chines.toCharArray();     
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();     
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);     
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);     
        for (int i = 0; i < nameChar.length; i++) {     
        	//判断字符是否是汉字
        	if (String.valueOf(nameChar[i]).matches("[\u4e00-\u9fa5]")) {     
                try {   
                	if(String.valueOf(nameChar[i]).equals("长")){
                		pinyinName += PinyinHelper.toHanyuPinyinStringArray('常', defaultFormat)[0];                                
                	}else{
                	    pinyinName += PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0];     
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {     
                    e.printStackTrace();     
                }     
            }else{     
                pinyinName += nameChar[i];     
            }     
        }     
        return pinyinName;     
    } 
	
    public boolean check(char name){
    	if(String.valueOf(name).matches("[\u4e00-\u9fa5]")){
    		return true;
    	}else{
        	return false;
    	}
    }
    
	    
    public void testPinYin() throws Exception{
    	//String str = "机场线路1号线";
    	//String str = "中关村E世界";
    	//String pinyin = converterToSpell(str);
    	//String pinyin1 = converterToFirstSpell(str);
    	char c = '（';
    	boolean result = check(c);
    	
    	Log.i(TAG, ""+result);
    }
    
    
    
}
