以下服务主要分为xbet跟商户项目
xbet项目是主要产品
商户项目主要围绕着xbet进行
api-gateway    ->  xbet-项目接口主要网关
ash            ->  新潘多拉项目
back           ->  xbet管理后台
back-gateway   ->  管理后台网关
back-oauth2    ->  管理后台认证
backforbusiness->  商户-后台
base           ->  基础包(所有服务打包前，先打包这个)
bplan          ->  xbet-主服务
datacenter     ->  xbet-数据中心,给接入方提供查询数据
gateway        ->  商户-网关，提供给接入方使用
netty          ->  xbet-websocket广播数据
oauth2         ->  商户-oauth2权限认证

项目需要依赖consul和rabbitmq
