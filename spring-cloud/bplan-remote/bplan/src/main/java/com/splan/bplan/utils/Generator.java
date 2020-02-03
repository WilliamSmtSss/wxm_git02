package com.splan.bplan.utils;

import org.mybatis.generator.api.ShellRunner;

/**
 * mybatis扩展生成工具
 */
public class Generator {

    public static void main(String[] args) {
        args = new String[] { "-configfile", "/Users/xx/IdeaProjects/plan/bplan/src/main/resources/mybatis-generator/mybatis-generator.xml", "-overwrite" };
        ShellRunner.main(args);

    }

}
