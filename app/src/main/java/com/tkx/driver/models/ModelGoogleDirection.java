package com.tkx.driver.models;

import java.util.List;

public class ModelGoogleDirection {

    /**
     * geocoded_waypoints : [{"geocoder_status":"OK","place_id":"ChIJj0VxeFPjDDkRtqwiuOIY-kI","types":["establishment","point_of_interest"]},{"geocoder_status":"OK","place_id":"ChIJQxu2j6vkDDkRzkPFvW_O53s","types":["route"]}]
     * routes : [{"bounds":{"northeast":{"lat":28.6267986,"lng":77.2918416},"southwest":{"lat":28.6216071,"lng":77.2800719}},"copyrights":"Map data ©2020","legs":[{"distance":{"text":"1.9 km","value":1931},"duration":{"text":"8 mins","value":456},"end_address":"Unnamed Road, Fazalpur, Fajalpur Village, Vinod Nagar North, New Delhi, Delhi 110092, India","end_location":{"lat":28.626674,"lng":77.2918416},"start_address":"U-38, SHAKARPUR , near laxmi nagar metro station, Block S1, Nanakpura, Shakarpur, Delhi, 110092, India","start_location":{"lat":28.6265772,"lng":77.2800719},"steps":[{"distance":{"text":"0.7 km","value":721},"duration":{"text":"1 min","value":77},"end_location":{"lat":28.6216071,"lng":77.2847961},"html_instructions":"Head <b>southeast<\/b> on <b>Main Mother Dairy Rd<\/b>/<wbr/><b>New Patpar Ganj Rd<\/b>","polyline":{"points":"ccvmDmwtvMrA{AFGPOl@o@vAyAd@e@JMPQRSFGh@i@\\_@BCXYz@{@JMNMJKXWJInAoADEX[X[ZWLMXWd@[HGb@[t@k@^Sd@W"},"start_location":{"lat":28.6265772,"lng":77.2800719},"travel_mode":"DRIVING"},{"distance":{"text":"0.4 km","value":437},"duration":{"text":"1 min","value":84},"end_location":{"lat":28.6253829,"lng":77.2858256},"html_instructions":"Turn <b>left<\/b> onto <b>Patpar Ganj Rd<\/b><div style=\"font-size:0.9em\">Pass by ICICI BANK ATM (on the left)<\/div>","maneuver":"turn-left","polyline":{"points":"adumD_uuvMAECECCCESOECEAoBa@k@Kg@MOCc@G_@EQCg@GaAM_@Eq@MyASE?eBOWA"},"start_location":{"lat":28.6216071,"lng":77.2847961},"travel_mode":"DRIVING"},{"distance":{"text":"0.2 km","value":168},"duration":{"text":"1 min","value":46},"end_location":{"lat":28.6267613,"lng":77.28618689999999},"html_instructions":"Turn <b>right<\/b>","maneuver":"turn-right","polyline":{"points":"s{umDm{uvMGUIOCACEECEAEAs@CmBGeAE"},"start_location":{"lat":28.6253829,"lng":77.2858256},"travel_mode":"DRIVING"},{"distance":{"text":"0.3 km","value":282},"duration":{"text":"2 mins","value":92},"end_location":{"lat":28.6261834,"lng":77.2887336},"html_instructions":"Turn <b>right<\/b>","maneuver":"turn-right","polyline":{"points":"gdvmDu}uvM?k@AuCAaBCaA?C?M?G@I@GBG?ABCPSVY^Ud@W"},"start_location":{"lat":28.6267613,"lng":77.28618689999999},"travel_mode":"DRIVING"},{"distance":{"text":"0.2 km","value":184},"duration":{"text":"2 mins","value":98},"end_location":{"lat":28.6267967,"lng":77.290425},"html_instructions":"Turn <b>left<\/b> onto <b>Gali Number 5<\/b>","maneuver":"turn-left","polyline":{"points":"s`vmDqmvvM]eAUg@Oc@KYAC@GBO?[KYSaAAE?AAACAAACA?AA?"},"start_location":{"lat":28.6261834,"lng":77.2887336},"travel_mode":"DRIVING"},{"distance":{"text":"0.1 km","value":139},"duration":{"text":"1 min","value":59},"end_location":{"lat":28.626674,"lng":77.2918416},"html_instructions":"Turn <b>right<\/b><div style=\"font-size:0.9em\">Destination will be on the right<\/div>","maneuver":"turn-right","polyline":{"points":"odvmDcxvvM?A?C?AFo@?U?UJaA@]@Y?U?S@S"},"start_location":{"lat":28.6267967,"lng":77.290425},"travel_mode":"DRIVING"}],"traffic_speed_entry":[],"via_waypoint":[]}],"overview_polyline":{"points":"ccvmDmwtvMzCcDzC_DhEmE`A{@hCmChByAbCcBd@WAEGIWUKEsE_A}BYaBSkCa@kBOWAGUMQIImDOeAE?k@CwFC{AF[TWVY^Ud@W]eAe@kAM]DW?[KYUgAGGEEFkAJwABw@@}@"},"summary":"Main Mother Dairy Rd/New Patpar Ganj Rd","warnings":[],"waypoint_order":[]}]
     * status : OK
     */

    private String status;
    private List<GeocodedWaypointsBean> geocoded_waypoints;
    private List<RoutesBean> routes;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<GeocodedWaypointsBean> getGeocoded_waypoints() {
        return geocoded_waypoints;
    }

    public void setGeocoded_waypoints(List<GeocodedWaypointsBean> geocoded_waypoints) {
        this.geocoded_waypoints = geocoded_waypoints;
    }

    public List<RoutesBean> getRoutes() {
        return routes;
    }

    public void setRoutes(List<RoutesBean> routes) {
        this.routes = routes;
    }

    public static class GeocodedWaypointsBean {
        /**
         * geocoder_status : OK
         * place_id : ChIJj0VxeFPjDDkRtqwiuOIY-kI
         * types : ["establishment","point_of_interest"]
         */

        private String geocoder_status;
        private String place_id;
        private List<String> types;

        public String getGeocoder_status() {
            return geocoder_status;
        }

        public void setGeocoder_status(String geocoder_status) {
            this.geocoder_status = geocoder_status;
        }

        public String getPlace_id() {
            return place_id;
        }

        public void setPlace_id(String place_id) {
            this.place_id = place_id;
        }

        public List<String> getTypes() {
            return types;
        }

        public void setTypes(List<String> types) {
            this.types = types;
        }
    }

    public static class RoutesBean {
        /**
         * bounds : {"northeast":{"lat":28.6267986,"lng":77.2918416},"southwest":{"lat":28.6216071,"lng":77.2800719}}
         * copyrights : Map data ©2020
         * legs : [{"distance":{"text":"1.9 km","value":1931},"duration":{"text":"8 mins","value":456},"end_address":"Unnamed Road, Fazalpur, Fajalpur Village, Vinod Nagar North, New Delhi, Delhi 110092, India","end_location":{"lat":28.626674,"lng":77.2918416},"start_address":"U-38, SHAKARPUR , near laxmi nagar metro station, Block S1, Nanakpura, Shakarpur, Delhi, 110092, India","start_location":{"lat":28.6265772,"lng":77.2800719},"steps":[{"distance":{"text":"0.7 km","value":721},"duration":{"text":"1 min","value":77},"end_location":{"lat":28.6216071,"lng":77.2847961},"html_instructions":"Head <b>southeast<\/b> on <b>Main Mother Dairy Rd<\/b>/<wbr/><b>New Patpar Ganj Rd<\/b>","polyline":{"points":"ccvmDmwtvMrA{AFGPOl@o@vAyAd@e@JMPQRSFGh@i@\\_@BCXYz@{@JMNMJKXWJInAoADEX[X[ZWLMXWd@[HGb@[t@k@^Sd@W"},"start_location":{"lat":28.6265772,"lng":77.2800719},"travel_mode":"DRIVING"},{"distance":{"text":"0.4 km","value":437},"duration":{"text":"1 min","value":84},"end_location":{"lat":28.6253829,"lng":77.2858256},"html_instructions":"Turn <b>left<\/b> onto <b>Patpar Ganj Rd<\/b><div style=\"font-size:0.9em\">Pass by ICICI BANK ATM (on the left)<\/div>","maneuver":"turn-left","polyline":{"points":"adumD_uuvMAECECCCESOECEAoBa@k@Kg@MOCc@G_@EQCg@GaAM_@Eq@MyASE?eBOWA"},"start_location":{"lat":28.6216071,"lng":77.2847961},"travel_mode":"DRIVING"},{"distance":{"text":"0.2 km","value":168},"duration":{"text":"1 min","value":46},"end_location":{"lat":28.6267613,"lng":77.28618689999999},"html_instructions":"Turn <b>right<\/b>","maneuver":"turn-right","polyline":{"points":"s{umDm{uvMGUIOCACEECEAEAs@CmBGeAE"},"start_location":{"lat":28.6253829,"lng":77.2858256},"travel_mode":"DRIVING"},{"distance":{"text":"0.3 km","value":282},"duration":{"text":"2 mins","value":92},"end_location":{"lat":28.6261834,"lng":77.2887336},"html_instructions":"Turn <b>right<\/b>","maneuver":"turn-right","polyline":{"points":"gdvmDu}uvM?k@AuCAaBCaA?C?M?G@I@GBG?ABCPSVY^Ud@W"},"start_location":{"lat":28.6267613,"lng":77.28618689999999},"travel_mode":"DRIVING"},{"distance":{"text":"0.2 km","value":184},"duration":{"text":"2 mins","value":98},"end_location":{"lat":28.6267967,"lng":77.290425},"html_instructions":"Turn <b>left<\/b> onto <b>Gali Number 5<\/b>","maneuver":"turn-left","polyline":{"points":"s`vmDqmvvM]eAUg@Oc@KYAC@GBO?[KYSaAAE?AAACAAACA?AA?"},"start_location":{"lat":28.6261834,"lng":77.2887336},"travel_mode":"DRIVING"},{"distance":{"text":"0.1 km","value":139},"duration":{"text":"1 min","value":59},"end_location":{"lat":28.626674,"lng":77.2918416},"html_instructions":"Turn <b>right<\/b><div style=\"font-size:0.9em\">Destination will be on the right<\/div>","maneuver":"turn-right","polyline":{"points":"odvmDcxvvM?A?C?AFo@?U?UJaA@]@Y?U?S@S"},"start_location":{"lat":28.6267967,"lng":77.290425},"travel_mode":"DRIVING"}],"traffic_speed_entry":[],"via_waypoint":[]}]
         * overview_polyline : {"points":"ccvmDmwtvMzCcDzC_DhEmE`A{@hCmChByAbCcBd@WAEGIWUKEsE_A}BYaBSkCa@kBOWAGUMQIImDOeAE?k@CwFC{AF[TWVY^Ud@W]eAe@kAM]DW?[KYUgAGGEEFkAJwABw@@}@"}
         * summary : Main Mother Dairy Rd/New Patpar Ganj Rd
         * warnings : []
         * waypoint_order : []
         */

        private BoundsBean bounds;
        private String copyrights;
        private OverviewPolylineBean overview_polyline;
        private String summary;
        private List<LegsBean> legs;
        private List<?> warnings;
        private List<?> waypoint_order;

        public BoundsBean getBounds() {
            return bounds;
        }

        public void setBounds(BoundsBean bounds) {
            this.bounds = bounds;
        }

        public String getCopyrights() {
            return copyrights;
        }

        public void setCopyrights(String copyrights) {
            this.copyrights = copyrights;
        }

        public OverviewPolylineBean getOverview_polyline() {
            return overview_polyline;
        }

        public void setOverview_polyline(OverviewPolylineBean overview_polyline) {
            this.overview_polyline = overview_polyline;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public List<LegsBean> getLegs() {
            return legs;
        }

        public void setLegs(List<LegsBean> legs) {
            this.legs = legs;
        }

        public List<?> getWarnings() {
            return warnings;
        }

        public void setWarnings(List<?> warnings) {
            this.warnings = warnings;
        }

        public List<?> getWaypoint_order() {
            return waypoint_order;
        }

        public void setWaypoint_order(List<?> waypoint_order) {
            this.waypoint_order = waypoint_order;
        }

        public static class BoundsBean {
            /**
             * northeast : {"lat":28.6267986,"lng":77.2918416}
             * southwest : {"lat":28.6216071,"lng":77.2800719}
             */

            private NortheastBean northeast;
            private SouthwestBean southwest;

            public NortheastBean getNortheast() {
                return northeast;
            }

            public void setNortheast(NortheastBean northeast) {
                this.northeast = northeast;
            }

            public SouthwestBean getSouthwest() {
                return southwest;
            }

            public void setSouthwest(SouthwestBean southwest) {
                this.southwest = southwest;
            }

            public static class NortheastBean {
                /**
                 * lat : 28.6267986
                 * lng : 77.2918416
                 */

                private double lat;
                private double lng;

                public double getLat() {
                    return lat;
                }

                public void setLat(double lat) {
                    this.lat = lat;
                }

                public double getLng() {
                    return lng;
                }

                public void setLng(double lng) {
                    this.lng = lng;
                }
            }

            public static class SouthwestBean {
                /**
                 * lat : 28.6216071
                 * lng : 77.2800719
                 */

                private double lat;
                private double lng;

                public double getLat() {
                    return lat;
                }

                public void setLat(double lat) {
                    this.lat = lat;
                }

                public double getLng() {
                    return lng;
                }

                public void setLng(double lng) {
                    this.lng = lng;
                }
            }
        }

        public static class OverviewPolylineBean {
            /**
             * points : ccvmDmwtvMzCcDzC_DhEmE`A{@hCmChByAbCcBd@WAEGIWUKEsE_A}BYaBSkCa@kBOWAGUMQIImDOeAE?k@CwFC{AF[TWVY^Ud@W]eAe@kAM]DW?[KYUgAGGEEFkAJwABw@@}@
             */

            private String points;

            public String getPoints() {
                return points;
            }

            public void setPoints(String points) {
                this.points = points;
            }
        }

        public static class LegsBean {
            /**
             * distance : {"text":"1.9 km","value":1931}
             * duration : {"text":"8 mins","value":456}
             * end_address : Unnamed Road, Fazalpur, Fajalpur Village, Vinod Nagar North, New Delhi, Delhi 110092, India
             * end_location : {"lat":28.626674,"lng":77.2918416}
             * start_address : U-38, SHAKARPUR , near laxmi nagar metro station, Block S1, Nanakpura, Shakarpur, Delhi, 110092, India
             * start_location : {"lat":28.6265772,"lng":77.2800719}
             * steps : [{"distance":{"text":"0.7 km","value":721},"duration":{"text":"1 min","value":77},"end_location":{"lat":28.6216071,"lng":77.2847961},"html_instructions":"Head <b>southeast<\/b> on <b>Main Mother Dairy Rd<\/b>/<wbr/><b>New Patpar Ganj Rd<\/b>","polyline":{"points":"ccvmDmwtvMrA{AFGPOl@o@vAyAd@e@JMPQRSFGh@i@\\_@BCXYz@{@JMNMJKXWJInAoADEX[X[ZWLMXWd@[HGb@[t@k@^Sd@W"},"start_location":{"lat":28.6265772,"lng":77.2800719},"travel_mode":"DRIVING"},{"distance":{"text":"0.4 km","value":437},"duration":{"text":"1 min","value":84},"end_location":{"lat":28.6253829,"lng":77.2858256},"html_instructions":"Turn <b>left<\/b> onto <b>Patpar Ganj Rd<\/b><div style=\"font-size:0.9em\">Pass by ICICI BANK ATM (on the left)<\/div>","maneuver":"turn-left","polyline":{"points":"adumD_uuvMAECECCCESOECEAoBa@k@Kg@MOCc@G_@EQCg@GaAM_@Eq@MyASE?eBOWA"},"start_location":{"lat":28.6216071,"lng":77.2847961},"travel_mode":"DRIVING"},{"distance":{"text":"0.2 km","value":168},"duration":{"text":"1 min","value":46},"end_location":{"lat":28.6267613,"lng":77.28618689999999},"html_instructions":"Turn <b>right<\/b>","maneuver":"turn-right","polyline":{"points":"s{umDm{uvMGUIOCACEECEAEAs@CmBGeAE"},"start_location":{"lat":28.6253829,"lng":77.2858256},"travel_mode":"DRIVING"},{"distance":{"text":"0.3 km","value":282},"duration":{"text":"2 mins","value":92},"end_location":{"lat":28.6261834,"lng":77.2887336},"html_instructions":"Turn <b>right<\/b>","maneuver":"turn-right","polyline":{"points":"gdvmDu}uvM?k@AuCAaBCaA?C?M?G@I@GBG?ABCPSVY^Ud@W"},"start_location":{"lat":28.6267613,"lng":77.28618689999999},"travel_mode":"DRIVING"},{"distance":{"text":"0.2 km","value":184},"duration":{"text":"2 mins","value":98},"end_location":{"lat":28.6267967,"lng":77.290425},"html_instructions":"Turn <b>left<\/b> onto <b>Gali Number 5<\/b>","maneuver":"turn-left","polyline":{"points":"s`vmDqmvvM]eAUg@Oc@KYAC@GBO?[KYSaAAE?AAACAAACA?AA?"},"start_location":{"lat":28.6261834,"lng":77.2887336},"travel_mode":"DRIVING"},{"distance":{"text":"0.1 km","value":139},"duration":{"text":"1 min","value":59},"end_location":{"lat":28.626674,"lng":77.2918416},"html_instructions":"Turn <b>right<\/b><div style=\"font-size:0.9em\">Destination will be on the right<\/div>","maneuver":"turn-right","polyline":{"points":"odvmDcxvvM?A?C?AFo@?U?UJaA@]@Y?U?S@S"},"start_location":{"lat":28.6267967,"lng":77.290425},"travel_mode":"DRIVING"}]
             * traffic_speed_entry : []
             * via_waypoint : []
             */

            private DistanceBean distance;
            private DurationBean duration;
            private String end_address;
            private EndLocationBean end_location;
            private String start_address;
            private StartLocationBean start_location;
            private List<StepsBean> steps;
            private List<?> traffic_speed_entry;
            private List<?> via_waypoint;

            public DistanceBean getDistance() {
                return distance;
            }

            public void setDistance(DistanceBean distance) {
                this.distance = distance;
            }

            public DurationBean getDuration() {
                return duration;
            }

            public void setDuration(DurationBean duration) {
                this.duration = duration;
            }

            public String getEnd_address() {
                return end_address;
            }

            public void setEnd_address(String end_address) {
                this.end_address = end_address;
            }

            public EndLocationBean getEnd_location() {
                return end_location;
            }

            public void setEnd_location(EndLocationBean end_location) {
                this.end_location = end_location;
            }

            public String getStart_address() {
                return start_address;
            }

            public void setStart_address(String start_address) {
                this.start_address = start_address;
            }

            public StartLocationBean getStart_location() {
                return start_location;
            }

            public void setStart_location(StartLocationBean start_location) {
                this.start_location = start_location;
            }

            public List<StepsBean> getSteps() {
                return steps;
            }

            public void setSteps(List<StepsBean> steps) {
                this.steps = steps;
            }

            public List<?> getTraffic_speed_entry() {
                return traffic_speed_entry;
            }

            public void setTraffic_speed_entry(List<?> traffic_speed_entry) {
                this.traffic_speed_entry = traffic_speed_entry;
            }

            public List<?> getVia_waypoint() {
                return via_waypoint;
            }

            public void setVia_waypoint(List<?> via_waypoint) {
                this.via_waypoint = via_waypoint;
            }

            public static class DistanceBean {
                /**
                 * text : 1.9 km
                 * value : 1931
                 */

                private String text;
                private int value;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public int getValue() {
                    return value;
                }

                public void setValue(int value) {
                    this.value = value;
                }
            }

            public static class DurationBean {
                /**
                 * text : 8 mins
                 * value : 456
                 */

                private String text;
                private int value;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public int getValue() {
                    return value;
                }

                public void setValue(int value) {
                    this.value = value;
                }
            }

            public static class EndLocationBean {
                /**
                 * lat : 28.626674
                 * lng : 77.2918416
                 */

                private double lat;
                private double lng;

                public double getLat() {
                    return lat;
                }

                public void setLat(double lat) {
                    this.lat = lat;
                }

                public double getLng() {
                    return lng;
                }

                public void setLng(double lng) {
                    this.lng = lng;
                }
            }

            public static class StartLocationBean {
                /**
                 * lat : 28.6265772
                 * lng : 77.2800719
                 */

                private double lat;
                private double lng;

                public double getLat() {
                    return lat;
                }

                public void setLat(double lat) {
                    this.lat = lat;
                }

                public double getLng() {
                    return lng;
                }

                public void setLng(double lng) {
                    this.lng = lng;
                }
            }

            public static class StepsBean {
                /**
                 * distance : {"text":"0.7 km","value":721}
                 * duration : {"text":"1 min","value":77}
                 * end_location : {"lat":28.6216071,"lng":77.2847961}
                 * html_instructions : Head <b>southeast</b> on <b>Main Mother Dairy Rd</b>/<wbr/><b>New Patpar Ganj Rd</b>
                 * polyline : {"points":"ccvmDmwtvMrA{AFGPOl@o@vAyAd@e@JMPQRSFGh@i@\\_@BCXYz@{@JMNMJKXWJInAoADEX[X[ZWLMXWd@[HGb@[t@k@^Sd@W"}
                 * start_location : {"lat":28.6265772,"lng":77.2800719}
                 * travel_mode : DRIVING
                 * maneuver : turn-left
                 */

                private DistanceBeanX distance;
                private DurationBeanX duration;
                private EndLocationBeanX end_location;
                private String html_instructions;
                private PolylineBean polyline;
                private StartLocationBeanX start_location;
                private String travel_mode;
                private String maneuver;

                public DistanceBeanX getDistance() {
                    return distance;
                }

                public void setDistance(DistanceBeanX distance) {
                    this.distance = distance;
                }

                public DurationBeanX getDuration() {
                    return duration;
                }

                public void setDuration(DurationBeanX duration) {
                    this.duration = duration;
                }

                public EndLocationBeanX getEnd_location() {
                    return end_location;
                }

                public void setEnd_location(EndLocationBeanX end_location) {
                    this.end_location = end_location;
                }

                public String getHtml_instructions() {
                    return html_instructions;
                }

                public void setHtml_instructions(String html_instructions) {
                    this.html_instructions = html_instructions;
                }

                public PolylineBean getPolyline() {
                    return polyline;
                }

                public void setPolyline(PolylineBean polyline) {
                    this.polyline = polyline;
                }

                public StartLocationBeanX getStart_location() {
                    return start_location;
                }

                public void setStart_location(StartLocationBeanX start_location) {
                    this.start_location = start_location;
                }

                public String getTravel_mode() {
                    return travel_mode;
                }

                public void setTravel_mode(String travel_mode) {
                    this.travel_mode = travel_mode;
                }

                public String getManeuver() {
                    return maneuver;
                }

                public void setManeuver(String maneuver) {
                    this.maneuver = maneuver;
                }

                public static class DistanceBeanX {
                    /**
                     * text : 0.7 km
                     * value : 721
                     */

                    private String text;
                    private int value;

                    public String getText() {
                        return text;
                    }

                    public void setText(String text) {
                        this.text = text;
                    }

                    public int getValue() {
                        return value;
                    }

                    public void setValue(int value) {
                        this.value = value;
                    }
                }

                public static class DurationBeanX {
                    /**
                     * text : 1 min
                     * value : 77
                     */

                    private String text;
                    private int value;

                    public String getText() {
                        return text;
                    }

                    public void setText(String text) {
                        this.text = text;
                    }

                    public int getValue() {
                        return value;
                    }

                    public void setValue(int value) {
                        this.value = value;
                    }
                }

                public static class EndLocationBeanX {
                    /**
                     * lat : 28.6216071
                     * lng : 77.2847961
                     */

                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }

                public static class PolylineBean {
                    /**
                     * points : ccvmDmwtvMrA{AFGPOl@o@vAyAd@e@JMPQRSFGh@i@\_@BCXYz@{@JMNMJKXWJInAoADEX[X[ZWLMXWd@[HGb@[t@k@^Sd@W
                     */

                    private String points;

                    public String getPoints() {
                        return points;
                    }

                    public void setPoints(String points) {
                        this.points = points;
                    }
                }

                public static class StartLocationBeanX {
                    /**
                     * lat : 28.6265772
                     * lng : 77.2800719
                     */

                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }
            }
        }
    }
}
