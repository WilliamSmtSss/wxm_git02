drop procedure IF EXISTS month_task;
CREATE DEFINER=`devuser`@`%` PROCEDURE `month_task`()
BEGIN
	#DECLARE stop_flag int DEFAULT 0;
	DECLARE done INT DEFAULT FALSE;
	DECLARE memberLevel int(11);
  DECLARE availableCoin DECIMAL(15,2);
  DECLARE coin1 DECIMAL(15,2);
  DECLARE limitCoin DECIMAL(15,2);
  DECLARE giftCoin DECIMAL(15,2);
	DECLARE everyMonthGift int(11) DEFAULT 0;
  DECLARE userId BIGINT DEFAULT 0;
	DECLARE balanceId BIGINT DEFAULT 0;
	DECLARE thisMonth VARCHAR(10);
	##查询结果
	DECLARE result1 CURSOR FOR  SELECT * from (SELECT a.id as userId,b.id as balanceId,a.member_level as memberLevel,b.available_coin as availableCoin,b.coin as coin1,b.limit_coin as limitCoin
	,b.gift_coin as giftCoin,c.every_month_gift as everyMonthGift FROM user_account as a
	LEFT JOIN user_balance  as b on a.id=b.user_id
	LEFT JOIN member_interests as c on a.member_level=c.id
	WHERE `status`!='DISABLE' AND member_status='ENABLE' AND every_month_gift>0) as a;
	declare CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
	SELECT CONCAT(YEAR(CURDATE()),'-',MONTH(CURDATE())) into thisMonth;
	OPEN result1;
		#WHILE stop_flag<>1 DO
		read_loop: LOOP
		  FETCH result1 into userId,balanceId,memberLevel,availableCoin,coin1,limitCoin,giftCoin,everyMonthGift;
			if done then
			 leave read_loop;
		  end if;
			#增加流水记录
			INSERT INTO money_record(balance_id,moneyable_id,moneyable_type,amount,money_from,`algorithm`,money_to,modify_id,detail,create_time,update_time) VALUE(
				balanceId,memberLevel,'MONTH_GIFT',everyMonthGift,coin1,'ADD',coin1+everyMonthGift,userId,CONCAT(thisMonth,'每月会员奖励'),NOW(),NOW()
			);
			#更新
			update user_balance set available_coin=available_coin+everyMonthGift,coin=coin1+everyMonthGift,gift_coin=gift_coin+everyMonthGift,
			limit_coin=limit_coin+everyMonthGift where user_id=userId;
			#重置会员
			UPDATE user_account set member_status='LOCK' where id=userId;
		#END WHILE;
		end LOOP;
	CLOSE result1;
END