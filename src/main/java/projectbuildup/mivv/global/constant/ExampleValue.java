package projectbuildup.mivv.global.constant;

import projectbuildup.mivv.domain.auth.service.BasicCertificationSystem;
import projectbuildup.mivv.domain.member.entity.IdentityVerification;

public class ExampleValue {

    public static class User {
        public final static String PASSWORD = "123456";
        public final static String NICKNAME = "hello";
        public final static String EMAIL = "temp@naver.com";
    }

    public static class Video{
        public final static String URL = "/static/videos/sample-m3u8/sample.m3u8";
    }

    public static class Image{
        public final static String SAMPLE = "/static/images/default/sample.jpg";
        public final static String THUMBNAIL = "/static/images/default/thumbnail.jpg";
        public final static String PROFILE_IMAGE = "/static/images/default/profile_image.jpg";
        public final static String DESCRIPTION_IMAGE = "/static/images/default/description.jpg";
    }

    public static class Time{
        public final static String DATE = "2023.02.02";
    }

    public static class JWT{
        public final static String ACCESS = "eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJzdWIiOiIxIiwiaWF0IjoxNjc1MzAwMTIxLCJleHAiOjE3MDY4MzYxMjEsImp0aSI6IjZlMTMwYmZiLTU3NDgtNDI0NS1iZjQ0LTYzMjk0MzVkOTQxYiIsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSIn0.uOgQjAj8hdt32hOUptLT8MgkJ5H9kms6Yx1WPOBL7K8";
        public final static String REFRESH = "eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJzdWIiOiIxIiwiaWF0IjoxNjc1MzAwMTIxLCJleHAiOjE3MDY4MzYxMjEsImp0aSI6ImU4YTY4MzMzLWE2ZWYtNDQwOC1hYjNkLWE1MWJiNjEyZTdkOCIsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSIn0.Rw3hOW6ldyV8uJbIMdv2w6buulSAPoh4a9KZaJ2gexg";
    }
}
