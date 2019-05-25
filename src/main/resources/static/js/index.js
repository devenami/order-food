var user = '';
$(function () {
    // 配置用户信息
    setUser();
    // 首次加载页面时，配置商品列表
    loadProductPage();
});

/**
 * 配置用户信息
 */
function setUser() {
    $_get('/public/user', function (data) {
        var code = data.code;
        if (code === 5000) {
            window.location.href = '/login.html';
            return;
        }
        user = data.data;
        $('#userName').text(user.username)
    });
}

/* ------------------------------------------------ 页面调用函数 ----------------------------------------------- */
/**
 * 加载现在订餐页面
 */
function tabNowToOrder() {
    loadProductPage();
}

/**
 * 退出登录
 */
function logout() {
    $_get("/public/logout", function (data) {
        var code = data.code;
        if (code === 200) {
            window.location.href = '/login.html';
        }
    });
}

/**
 * 加载下订单页面
 */
function loadProductPage() {
    var container = $('#container');
    container.empty();
    container.append(
        '<div>' +
        '   <!-- 用户订单 -->' +
        '   <div id="user_order">' +
        '   </div>' +
        '   <!-- 行开始 -->' +
        '   <div class="row" id="products">' +
        '   </div>' +
        '</div>'
    );

    loadUserOrder();
}

/**
 * 添加订单
 * @param btn 按钮对象
 */
function addOrder(btn) {
    var sibs = $(btn).siblings('input');
    var inputForProduct = sibs.get(1);
    var productId = $(inputForProduct).val();
    $_post('/order/add', {
        productId: productId
    }, function (data) {
        if (data.code === 200) {
            var order = data.data;
            // 绑定属性
            $(sibs.get(0)).val(order.orderId);
            // 隐藏预定按钮
            $(btn).hide();
            // 显示取消按钮
            var a = $(btn).siblings('a').get(0);
            $(a).css('display', 'block');
            // 联动订单，添加订单
            addOrderUI(order.orderId, order.productId, order.productName);
        } else {
            $(btn).text(data.msg);
        }
    })
}

/**
 * 移除订单
 */
function removeOrder(btn) {
    var sibs = $(btn).siblings('input');
    var inputForOrder = sibs.get(0);
    var orderId = $(inputForOrder).val();
    $_post('/order/delete', {
        orderId: orderId
    }, function (data) {
        if (data.code === 200) {
            // 绑定属性
            $(sibs.get(0)).val('');
            // 隐藏预定按钮
            $(btn).hide();
            // 显示取消按钮
            var a = $(btn).siblings('a').get(0);
            $(a).css('display', 'block');
            // 联动订单，移除订单
            removeOrderUI(orderId);
        } else {
            $(btn).text(data.msg);
        }

    })
}

/**
 * 移除今日订单 (已预订列表上)
 * @param element 点击的按钮
 */
function removeOrderAtOrder(element) {
    var inputs = $(element).children('input');
    var orderId = $(inputs.get(0)).val();
    $_post('/order/delete', {
        orderId: orderId
    }, function (data) {
        if (data.code === 200) {
            var productId = $(inputs.get(1)).val();
            var cols = $('#products').children();
            for (var i = 0; i < cols.length; i++) {
                var thumbnail = $(cols.get(i)).children().get(0);
                var caption = $(thumbnail).children().get(1);
                var p2 = $(caption).children('p').get(1);
                var productInput = $(p2).children('input').get(1);
                if (productId === $(productInput).val()) {
                    var as = $(p2).children('a');
                    $(as.get(0)).css('display', 'block');
                    $(as.get(1)).hide();
                }
            }
            $(element).parent().remove();
        } else {
            $(element).text(data.msg);
        }
    });

}

/* ------------------------------------------------ 帮助函数 ----------------------------------------------- */

/**
 * 加载用户今日的订单
 * arr.push(e) 可以向数组中增加元素
 */
function loadUserOrder() {
    var user_order = $('#user_order');
    user_order.empty();
    $_get('/order/list/day?day=' + 0, function (data) {
        if (data.code !== 200) {
            user_order.append('');
            loadProductList();
            return;
        }

        // 查询订单成功
        var list = data.data;
        var orderProductIdMap = [];
        for (var i in list) {
            var order = list[i];
            orderProductIdMap[order.productId] = order.orderId;
            addOrderUI(order.orderId, order.productId, order.productName);
        }
        loadProductList(orderProductIdMap);
    });
}

/**
 * 添加订单UI
 */
function addOrderUI(orderId, productId, productName) {
    var container = $('#user_order');
    var row = $('#user_order .row');
    var appendHtml = '';
    var before = container.html();
    if (!before) {
        appendHtml += '<h4>已预订: (点击对应的选项取消)</h4>' +
            '<div class="row" style="margin: 10px">';
    }

    appendHtml += '<div style="display: inline-block;margin: 10px">' +
        '               <button type="button" class="btn btn-default btn-md" onclick="removeOrderAtOrder(this)">' +
        '                  <span class="glyphicon glyphicon-star" aria-hidden="true"></span>' +
        '                  <input name="orderId" type="hidden" value="' + orderId + '">' +
        '                  <input name="productId" type="hidden" value="' + productId + '">' +
        '                  <span>' + productName + '</span>' +
        '                  </button>' +
        '                </div>';

    if (!before) {
        appendHtml += '</div>';
        container.append(appendHtml);
    } else {
        row.append(appendHtml);
    }
}

/**
 * 移除订单UI
 * @param orderId 订单id
 */
function removeOrderUI(orderId) {
    var childes = $('#user_order .row').children();
    for (var i = 0; i < childes.length; i++) {
        var col = childes.get(i);
        var inputs = $(col).children().find('input');
        if (orderId === $(inputs.get(0)).val()) {
            $(col).remove();
            return;
        }
    }
}

/**
 * 加载商品列表
 * 使用 arr.indexOf() > -1 可检查元素是否在数组中
 * @param orderProductMap 用户今日的订单列表
 */
function loadProductList(orderProductMap) {
    if (!orderProductMap) {
        orderProductMap = [];
    }
    $_get('/product/list', function (data) {
        var row = $('#products');
        row.empty();
        if (data.code !== 200) {
            row.append("<h2>服务端发生错误,错误信息: [" + data.msg + "]</h2>");
            return;
        }
        var list = data.data;
        for (var i in list) {
            var prod = list[i];
            var pid = prod.productId;
            var name = prod.name;
            var price = prod.price;
            var imageSrc = $_asset(prod.image);
            var htmlPrefix = '<div class="col-sm-4 col-md-3">' +
                '                <div class="thumbnail">' +
                '                    <img src="' + imageSrc + '">' +
                '                    <div class="caption">' +
                '                        <h3>' + name + '</h3>' +
                '                        <p>￥：' + price + '</p>' +
                '                        <p>';
            var htmlBody = '';
            if (orderProductMap[pid]) {
                htmlBody =
                    '<input name="orderId" type="hidden" value="' + orderProductMap[pid] + '">' +
                    '<input name="productId" type="hidden"  value="' + pid + '">' +
                    '<a href="javascript:void(0)" class="btn btn-default btn-block btn-hidden" onclick="addOrder(this)">预定</a>' +
                    '<a href="javascript:void(0)" class="btn btn-success btn-block" onclick="removeOrder(this)">取消预定</a>';
            } else {
                htmlBody =
                    '<input name="orderId" type="hidden">' +
                    '<input name="productId" type="hidden"  value="' + pid + '">' +
                    '<a href="javascript:void(0)" class="btn btn-default btn-block"  onclick="addOrder(this)">预定</a>' +
                    '<a href="javascript:void(0)" class="btn btn-success btn-block btn-hidden" onclick="removeOrder(this)">取消预定</a>';
            }
            var htmlSuffix =
                '                        </p>' +
                '                    </div>' +
                '                </div>' +
                '            </div>';

            // 添加内容
            row.append(htmlPrefix + htmlBody + htmlSuffix);

        }
    })
}
