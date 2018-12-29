package com.example.administrator.essayjoke.model;

import java.util.List;

/**
 * Created by nana on 2018/9/1.
 */

public class DiscoverListResult {

    /**
     * c : {"c1":"101010100","c2":"beijing","c3":"北京","c4":"beijing","c5":"北京","c6":"beijing","c7":"北京","c8":"china","c9":"中国","c10":"1","c11":"010","c12":"100000","c13":"116.391","c14":"39.904","c15":"33","c16":"AZ9010","c17":"+8"}
     * f : {"f1":[{"fa":"01","fb":"03","fc":"10","fd":"5","fe":"0","ff":"0","fg":"0","fh":"0","fi":"06:21|17:40"},{"fa":"07","fb":"07","fc":"19","fd":"12","fe":"0","ff":"0","fg":"0","fh":"0","fi":"06:22|17:38"},{"fa":"02","fb":"00","fc":"15","fd":"5","fe":"8","ff":"8","fg":"3","fh":"1","fi":"06:23|17:37"},{"fa":"00","fb":"00","fc":"16","fd":"4","fe":"0","ff":"0","fg":"0","fh":"0","fi":"06:24|17:35"},{"fa":"00","fb":"00","fc":"18","fd":"7","fe":"0","ff":"0","fg":"0","fh":"0","fi":"06:25|17:34"},{"fa":"00","fb":"01","fc":"18","fd":"8","fe":"0","ff":"0","fg":"0","fh":"0","fi":"06:26|17:32"},{"fa":"01","fb":"01","fc":"16","fd":"6","fe":"0","ff":"0","fg":"0","fh":"0","fi":"06:27|17:31"}],"f0":"201310121100"}
     */

    private CBean c;
    private FBean f;

    public CBean getC() {
        return c;
    }

    public void setC(CBean c) {
        this.c = c;
    }

    public FBean getF() {
        return f;
    }

    public void setF(FBean f) {
        this.f = f;
    }

    public static class CBean {
        /**
         * c1 : 101010100
         * c2 : beijing
         * c3 : 北京
         * c4 : beijing
         * c5 : 北京
         * c6 : beijing
         * c7 : 北京
         * c8 : china
         * c9 : 中国
         * c10 : 1
         * c11 : 010
         * c12 : 100000
         * c13 : 116.391
         * c14 : 39.904
         * c15 : 33
         * c16 : AZ9010
         * c17 : +8
         */

        private String c1;
        private String c2;
        private String c3;
        private String c4;
        private String c5;
        private String c6;
        private String c7;
        private String c8;
        private String c9;
        private String c10;
        private String c11;
        private String c12;
        private String c13;
        private String c14;
        private String c15;
        private String c16;
        private String c17;

        public String getC1() {
            return c1;
        }

        public void setC1(String c1) {
            this.c1 = c1;
        }

        public String getC2() {
            return c2;
        }

        public void setC2(String c2) {
            this.c2 = c2;
        }

        public String getC3() {
            return c3;
        }

        public void setC3(String c3) {
            this.c3 = c3;
        }

        public String getC4() {
            return c4;
        }

        public void setC4(String c4) {
            this.c4 = c4;
        }

        public String getC5() {
            return c5;
        }

        public void setC5(String c5) {
            this.c5 = c5;
        }

        public String getC6() {
            return c6;
        }

        public void setC6(String c6) {
            this.c6 = c6;
        }

        public String getC7() {
            return c7;
        }

        public void setC7(String c7) {
            this.c7 = c7;
        }

        public String getC8() {
            return c8;
        }

        public void setC8(String c8) {
            this.c8 = c8;
        }

        public String getC9() {
            return c9;
        }

        public void setC9(String c9) {
            this.c9 = c9;
        }

        public String getC10() {
            return c10;
        }

        public void setC10(String c10) {
            this.c10 = c10;
        }

        public String getC11() {
            return c11;
        }

        public void setC11(String c11) {
            this.c11 = c11;
        }

        public String getC12() {
            return c12;
        }

        public void setC12(String c12) {
            this.c12 = c12;
        }

        public String getC13() {
            return c13;
        }

        public void setC13(String c13) {
            this.c13 = c13;
        }

        public String getC14() {
            return c14;
        }

        public void setC14(String c14) {
            this.c14 = c14;
        }

        public String getC15() {
            return c15;
        }

        public void setC15(String c15) {
            this.c15 = c15;
        }

        public String getC16() {
            return c16;
        }

        public void setC16(String c16) {
            this.c16 = c16;
        }

        public String getC17() {
            return c17;
        }

        public void setC17(String c17) {
            this.c17 = c17;
        }
    }

    public static class FBean {
        /**
         * f1 : [{"fa":"01","fb":"03","fc":"10","fd":"5","fe":"0","ff":"0","fg":"0","fh":"0","fi":"06:21|17:40"},{"fa":"07","fb":"07","fc":"19","fd":"12","fe":"0","ff":"0","fg":"0","fh":"0","fi":"06:22|17:38"},{"fa":"02","fb":"00","fc":"15","fd":"5","fe":"8","ff":"8","fg":"3","fh":"1","fi":"06:23|17:37"},{"fa":"00","fb":"00","fc":"16","fd":"4","fe":"0","ff":"0","fg":"0","fh":"0","fi":"06:24|17:35"},{"fa":"00","fb":"00","fc":"18","fd":"7","fe":"0","ff":"0","fg":"0","fh":"0","fi":"06:25|17:34"},{"fa":"00","fb":"01","fc":"18","fd":"8","fe":"0","ff":"0","fg":"0","fh":"0","fi":"06:26|17:32"},{"fa":"01","fb":"01","fc":"16","fd":"6","fe":"0","ff":"0","fg":"0","fh":"0","fi":"06:27|17:31"}]
         * f0 : 201310121100
         */

        private String f0;
        private List<F1Bean> f1;

        public String getF0() {
            return f0;
        }

        public void setF0(String f0) {
            this.f0 = f0;
        }

        public List<F1Bean> getF1() {
            return f1;
        }

        public void setF1(List<F1Bean> f1) {
            this.f1 = f1;
        }

        public static class F1Bean {
            /**
             * fa : 01
             * fb : 03
             * fc : 10
             * fd : 5
             * fe : 0
             * ff : 0
             * fg : 0
             * fh : 0
             * fi : 06:21|17:40
             */

            private String fa;
            private String fb;
            private String fc;
            private String fd;
            private String fe;
            private String ff;
            private String fg;
            private String fh;
            private String fi;

            public String getFa() {
                return fa;
            }

            public void setFa(String fa) {
                this.fa = fa;
            }

            public String getFb() {
                return fb;
            }

            public void setFb(String fb) {
                this.fb = fb;
            }

            public String getFc() {
                return fc;
            }

            public void setFc(String fc) {
                this.fc = fc;
            }

            public String getFd() {
                return fd;
            }

            public void setFd(String fd) {
                this.fd = fd;
            }

            public String getFe() {
                return fe;
            }

            public void setFe(String fe) {
                this.fe = fe;
            }

            public String getFf() {
                return ff;
            }

            public void setFf(String ff) {
                this.ff = ff;
            }

            public String getFg() {
                return fg;
            }

            public void setFg(String fg) {
                this.fg = fg;
            }

            public String getFh() {
                return fh;
            }

            public void setFh(String fh) {
                this.fh = fh;
            }

            public String getFi() {
                return fi;
            }

            public void setFi(String fi) {
                this.fi = fi;
            }
        }
    }
}
