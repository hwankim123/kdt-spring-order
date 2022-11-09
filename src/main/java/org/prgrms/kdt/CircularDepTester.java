//package org.prgrms.kdt;
//
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//public class CircularDepTester {
//
//    public static void main(String[] args) {
//        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(CircularConfig.class);
//    }
//
//    @Configuration
//    private static class CircularConfig {
//        @Bean
//        public A a(B b){
//            return new A(b);
//        }
//
//        @Bean
//        public B b(A a){
//            return new B(a);
//        }
//
//        public CircularConfig() {
//        }
//    }
//
//    private static class A {
//        private B b;
//
//        public A(B b) {
//            this.b = b;
//        }
//    }
//
//    private static class B {
//        private A a;
//
//        public B(A a) {
//            this.a = a;
//        }
//    }
//}
