package com.splan.xbet.admin.back.utils.export;

import com.splan.base.http.CommonResult;
import com.splan.xbet.admin.back.utils.ResultUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/exceptTest")
public class ExceptTest {
    @GetMapping("/test")
    public CommonResult<String> getTest(HttpServletRequest request, HttpServletResponse responses) throws Exception {
        List<Product> list = new ArrayList<>();

        for (int i = 0 ; i<60000 ; i++) {
            //组装测试数据
            Product product = new Product();
            product.setName("爱奇艺会员"+i);
            product.setPrice(9.99);
            product.setDate(new Date());
            list.add(product);
        }

        ReportExcel reportExcel = new ReportExcel();
        reportExcel.excelExport(list,"测试",Product.class,1,responses,request);

        return ResultUtil.returnSuccess("11111");
    }


}
