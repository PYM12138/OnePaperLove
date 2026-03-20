$(document).ready(function() {
    // 定义高亮类
    const activeClass = 'active';

    // 点击导航项时切换高亮
    $('#navbar_1 .nav-link').on('click', function(e) {


        e.preventDefault(); // ❗ 阻止默认跳转（关键）

        const href = $(this).attr('href');

        // 🔥 1. 终止正在进行的 Ajax
        if (window.currentRequest && typeof window.currentRequest.abort === 'function') {
            try { window.currentRequest.abort(); } catch (err) {}
        }

        // 🔥 2. 停止所有动画
        if (window.jQuery) {
            try { $('*').stop(true, true); } catch (err) {}
        }

        // 🔥 3. 高亮切换（你原来的逻辑）
        // 移除所有导航项的 active 类
        $('#navbar_1 .nav-link').removeClass(activeClass);
        // 为当前点击的导航项添加 active 类
        $(this).addClass(activeClass);

        // 🚀 4. 立即跳转（核心）
        window.location.href = href;
    });

    // 页面加载时，根据当前 URL 高亮导航项
    const currentPath = window.location.pathname;
    $('#navbar_1 .nav-link').each(function() {
        const href = $(this).attr('href');
        // 处理 Thymeleaf 的 @{} 解析后的相对路径
        if (href && (href === currentPath || href === currentPath.substring(1) || currentPath === '' && href === '/')) {
            $('#navbar_1 .nav-link').removeClass(activeClass);
            $(this).addClass(activeClass);
        }
    });
});


$('.navbar-nav>li>a').on('click', function () {
    // 折叠状态下，点击导航项后自动收起
    $('.navbar-collapse').collapse('hide');
});

$('.navbar-collapse').on('show.bs.collapse', function () {
    // 导航展开时，图标变为“X”
    $('.navbar-toggler-icon').css({
        'background-image': 'url("data:image/svg+xml,%3csvg xmlns=\'http://www.w3.org/2000/svg\' width=\'24\' height=\'24\' viewBox=\'0 0 24 24\'%3e%3cpath stroke=\'%23007bff\' stroke-linecap=\'round\' stroke-miterlimit=\'10\' stroke-width=\'2.5\' d=\'M2 2 L22 22\'/%3e%3cpath stroke=\'%23007bff\' stroke-linecap=\'round\' stroke-miterlimit=\'10\' stroke-width=\'2.5\' d=\'M2 22 L22 2\'/%3e%3c/svg%3e")',
        'transition': 'background-image 0.3s ease' // 添加平滑过渡
    });
});

$('.navbar-collapse').on('hide.bs.collapse', function () {
    // 导航收起时，图标恢复为“三横”
    $('.navbar-toggler-icon').css({
        'background-image': 'url("data:image/svg+xml,%3csvg xmlns=\'http://www.w3.org/2000/svg\' width=\'24\' height=\'24\' viewBox=\'0 0 24 24\'%3e%3cpath stroke=\'%23007bff\' stroke-linecap=\'round\' stroke-miterlimit=\'10\' stroke-width=\'2.5\' d=\'M3 6h18M3 12h18M3 18h18\'/%3e%3c/svg%3e")',
        'transition': 'background-image 0.3s ease' // 添加平滑过渡
    });
});

