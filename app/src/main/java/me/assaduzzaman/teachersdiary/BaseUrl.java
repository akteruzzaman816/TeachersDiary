package me.assaduzzaman.teachersdiary;

public  class BaseUrl {

    public String getLoginUrl(){

        final  String BASE_URL="http://tms.smartcloudiot.com/admin/mobilesignin";

        return BASE_URL;

    }

    public String getRoutineUrl(){

        final  String BASE_URL="http://tms.smartcloudiot.com/admin/routineapi";

        return BASE_URL;

    }
}