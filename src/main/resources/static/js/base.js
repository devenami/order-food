// 请求主机url [http|https]://ip:port/url
var host = '';
// var assets = '/assets';
var assets = '';
var default_image_url = '/image/default.jpg';

/**
 * 发送get请求
 * @param url 请求路径
 * @param fun 回调函数
 */
function $_get(url, fun) {
    var requestUrl = host + url;
    $.get(requestUrl, fun)
}

/**
 * 发送post请求
 * @param url 请求地址
 * @param param 请求参数
 * @param fun 回调函数
 */
function $_post(url, param, fun) {
    var requestUrl = host + url;
    $.post(requestUrl, param, fun)
}

/**
 * 请求静态资源
 * @param url 资源地址
 * @returns {string} 处理后的资源地址
 */
function $_asset(url) {
    if (!url) {
        url = default_image_url;
    }
    return host + assets + url;
}