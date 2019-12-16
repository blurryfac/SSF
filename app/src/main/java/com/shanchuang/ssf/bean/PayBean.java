package com.shanchuang.ssf.bean;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/10/30
 */
public class PayBean {
    /**
     * pay : alipay_sdk=alipay-sdk-php-20180705&app_id=2019072065932127&biz_content=%7B%22body%22%3A%22%5Cu8bfe%5Cu7a0b%5Cu8d2d%5Cu4e70-1572425769316705%22%2C%22subject%22%3A%22%5Cu8bfe%5Cu7a0b%5Cu8d2d%5Cu4e70-1572425769316705%22%2C%22out_trade_no%22%3A%221572425769316705%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A9.9%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fk12.w.shanchuang360.com%2Fk12%2Fpay%2Falipay_course.html&sign_type=RSA2&timestamp=2019-10-30+16%3A56%3A09&version=1.0&sign=obCAjAqwuLJJMwXjFqS1qrB9jjQO7G0y7k1huEJH4QbUW2mrtpc89hNGdC6T62ewxQjVgMlwyMU0TgSlVE4Cd%2BKIzPJRGCl%2BY6WKubrKCypXtrwOkxlol%2BBb%2Fkww6TaeNKKI9quzCCWT9GYWZZWOIAONtWPmjaCq593m7phz2byHo3wOleyh6mliMoOrFG%2BPDv9kFN4rAAbS%2B%2FlUk6%2FM8cd9dwwSbCNWlYiIjcz3TgwAlPokRFZW7FRXmUdT0F%2FqTPnIU3h9t%2FDHIy4ma3Pw3KiS6wQ%2F%2Fsct3uSanx8typBAzfLPUdHLpCnnt5hbZTPgWzq2ESJ2cj4zuDTp%2F8k0pw%3D%3D
     */

    private String pay;

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }
}
