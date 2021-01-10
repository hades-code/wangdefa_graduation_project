package org.lhq.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author hades
 */
@Component
@Slf4j
public class AutoFillHandler implements MetaObjectHandler {
	@Override
	public void insertFill(MetaObject metaObject) {
		log.info("start insert fill ....");
		this.strictInsertFill(metaObject, "createTime", LocalDateTime::now,LocalDateTime.class);
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		log.info("start update fill ....");
		this.strictInsertFill(metaObject, "modifyTime", LocalDateTime::now,LocalDateTime.class);
		this.strictUpdateFill(metaObject, "modifyTime", LocalDateTime::now, LocalDateTime.class);
	}
}