/**
 *
 */
package com.dofun.sxl.util.xf;


import com.dofun.sxl.bean.xf.Result;

import java.text.DecimalFormat;

/**
 * <p>Title: ReadSentenceResult</p>
 * <p>Description: </p>
 * <p>Company: www.iflytek.com</p>
 *
 * @author iflytek
 * @date 2015年1月12日 下午5:04:14
 */
public class ReadSentenceResult extends Result {

    public ReadSentenceResult() {
        category = "read_sentence";
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        if ("cn".equals(language)) {
            buffer.append("[总体结果]\n")
                    .append("评测内容：" + content + "\n")
                    .append("朗读时长：" + save2digit((float) (time_len / 100.0)) + " 秒" + "\n")
                    .append("完整度分：" + floatToInt(integrity_score) + "\n")
                    .append("总分：" + floatToInt(total_score * 20) + "\n\n")
                    .append("[朗读详情]").append(ResultFormatUtil.formatDetails_CN(sentences));
        } else {
            if (is_rejected) {
                buffer.append("检测到乱读，")
                        .append("except_info:" + except_info + "\n\n");    // except_info代码说明详见《语音评测参数、结果说明文档》
            }

            buffer.append("[总体结果]\n")
                    .append("评测内容：" + content + "\n")
                    .append("总分：" + total_score + "\n\n")
                    .append("[朗读详情]").append(ResultFormatUtil.formatDetails_EN(sentences));
        }

        return buffer.toString();
    }

    private String save2digit(float num) {
        DecimalFormat decimalFormat = new DecimalFormat(".00");
        String goal = decimalFormat.format(num);
        if (goal.equals(".00")) {
            return "0.00";
        } else {
            return goal;
        }
    }

    private int floatToInt(float score) {
        int i = (int) ((score * 10 + 5) / 10);
        return i;
    }
}
