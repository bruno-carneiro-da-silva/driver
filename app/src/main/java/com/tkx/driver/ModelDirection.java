package com.tkx.driver;





public class ModelDirection {

    /**
     * version : 1.5
     * result : 1
     * message : Data fetched successfully
     * data : {"time":"1 hour 3 mins","time_in_min":3756,"distance":"38.1 km","distance_in_meter":38132,"poly_point":"wevmDkmtvMoA|@]b@OSc@o@_@m@kAnAkAnAOLaAx@aAl@q@n@ErA~FxJp@lCLnADnA?zKJv\\?t`@X`EBdE?da@CrBIz@K|A@vA?lD?^?^IfCIfDBlJAvB^|NF|ATbIJRDh@HHnAHzADj@Hn@TnAv@z@p@jHhFnRjMz@f@rMbJxM|I|A~@bAIpB_Ex@w@VO`AErGLjFP`@J`@PZVX\\p@vBvAdErAvDVdBAt@Sr@}A`DsAlC_AdCq@vB?\\Dt@Fd@bDnJb@hADAHAL?RHPRFf@I\\KLPfAjDbKpAtDjC|Hb@bAXx@`@Ch@Df@Lr@b@VXTj@Lt@Mt@U^]d@?pAlArDvF|OzA~EDRz@f@L?f@RPRFZ?^I\\IN@`AHVtAdEbBfFxB`GtCnId@r@VAl@PVXDRCj@MZUTm@LUC]h@}E|JcG`MkBbER^Hh@Kj@MRMLFLLZ~ChJzEpNZz@DAJAVAV@RJZd@Fv@EXEJ_@f@WL[@SDW\\W\\q@|Bm@jBSd@qCtDqErFwBlCsBdCtDrEnB|Cv@xAlDxHzEnLz@lBz@jB`Nz[b@~@bHdPfL`X`FdLtCxGnEfMdFtOrC`JdAfElBjHtDtL~BdI~AfFdDfKz@hBdAfBlB|BlAnA|@v@d@Zf@TvXxE~IdBlAZdCj@zEtAfBj@x@`@NJdAx@d@b@bA|Av@hBv@zB|BxHrC~GlFnK~ArCzAjBhA|@vAr@`AVhAPhAF|EJjJTbd@jAvALjC`@hMfCrLvCnGfBn@TlBdAl@h@v@v@hGnIbKnNtInLvChEx@zA|@pB~DxKtAtClAzAv@l@bAn@~Bt@~RjHjBp@vBjAx@p@bAjAjAtBhZ~s@t@vAr@`AlB`BbF|Cbe@`Y|OlJdF|CrMpHfDpBzKjGpCnAp@`@j@ZlC^nCFpBEzCk@bDgAlGkCpD_AbDi@nAMvABrAHdBVvBp@hB`AxA`AxH~FjIxGxPrOtJzIfKtJbGzFxJpJnPvO`TzRvFhF`FbFxM|L~FvFzFjHzGrJdBdC~B|CzCxEbEpFbEjFlDbFz@dAbNtQfBnB~AjAfFvCjCfBdA|@`V`UfKxJtSzRtSpRxGpGzSrRdD`Dj@NZHvCvCt@lAhA`C^jARbARrANpBL~DIlA}ASW`@a@XG?o@U[Ic@CSD_Bp@sFrBSBICCGa@HsIvCiEbBcHjCiG|BsHlC_C|@{BzAaDvBsDbCiAx@MJGMQ["}
     */

    private String version;
    private String result;
    private String message;
    private DataBean data;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * time : 1 hour 3 mins
         * time_in_min : 3756
         * distance : 38.1 km
         * distance_in_meter : 38132
         * poly_point : wevmDkmtvMoA|@]b@OSc@o@_@m@kAnAkAnAOLaAx@aAl@q@n@ErA~FxJp@lCLnADnA?zKJv\?t`@X`EBdE?da@CrBIz@K|A@vA?lD?^?^IfCIfDBlJAvB^|NF|ATbIJRDh@HHnAHzADj@Hn@TnAv@z@p@jHhFnRjMz@f@rMbJxM|I|A~@bAIpB_Ex@w@VO`AErGLjFP`@J`@PZVX\p@vBvAdErAvDVdBAt@Sr@}A`DsAlC_AdCq@vB?\Dt@Fd@bDnJb@hADAHAL?RHPRFf@I\KLPfAjDbKpAtDjC|Hb@bAXx@`@Ch@Df@Lr@b@VXTj@Lt@Mt@U^]d@?pAlArDvF|OzA~EDRz@f@L?f@RPRFZ?^I\IN@`AHVtAdEbBfFxB`GtCnId@r@VAl@PVXDRCj@MZUTm@LUC]h@}E|JcG`MkBbER^Hh@Kj@MRMLFLLZ~ChJzEpNZz@DAJAVAV@RJZd@Fv@EXEJ_@f@WL[@SDW\W\q@|Bm@jBSd@qCtDqErFwBlCsBdCtDrEnB|Cv@xAlDxHzEnLz@lBz@jB`Nz[b@~@bHdPfL`X`FdLtCxGnEfMdFtOrC`JdAfElBjHtDtL~BdI~AfFdDfKz@hBdAfBlB|BlAnA|@v@d@Zf@TvXxE~IdBlAZdCj@zEtAfBj@x@`@NJdAx@d@b@bA|Av@hBv@zB|BxHrC~GlFnK~ArCzAjBhA|@vAr@`AVhAPhAF|EJjJTbd@jAvALjC`@hMfCrLvCnGfBn@TlBdAl@h@v@v@hGnIbKnNtInLvChEx@zA|@pB~DxKtAtClAzAv@l@bAn@~Bt@~RjHjBp@vBjAx@p@bAjAjAtBhZ~s@t@vAr@`AlB`BbF|Cbe@`Y|OlJdF|CrMpHfDpBzKjGpCnAp@`@j@ZlC^nCFpBEzCk@bDgAlGkCpD_AbDi@nAMvABrAHdBVvBp@hB`AxA`AxH~FjIxGxPrOtJzIfKtJbGzFxJpJnPvO`TzRvFhF`FbFxM|L~FvFzFjHzGrJdBdC~B|CzCxEbEpFbEjFlDbFz@dAbNtQfBnB~AjAfFvCjCfBdA|@`V`UfKxJtSzRtSpRxGpGzSrRdD`Dj@NZHvCvCt@lAhA`C^jARbARrANpBL~DIlA}ASW`@a@XG?o@U[Ic@CSD_Bp@sFrBSBICCGa@HsIvCiEbBcHjCiG|BsHlC_C|@{BzAaDvBsDbCiAx@MJGMQ[
         */

        private String time;
        private int time_in_min;
        private String distance;
        private int distance_in_meter;
        private String poly_point;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getTime_in_min() {
            return time_in_min;
        }

        public void setTime_in_min(int time_in_min) {
            this.time_in_min = time_in_min;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public int getDistance_in_meter() {
            return distance_in_meter;
        }

        public void setDistance_in_meter(int distance_in_meter) {
            this.distance_in_meter = distance_in_meter;
        }

        public String getPoly_point() {
            return poly_point;
        }

        public void setPoly_point(String poly_point) {
            this.poly_point = poly_point;
        }
    }
}

