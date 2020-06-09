package com.india.covid9;

        public class StateModel {
            String Active, Confirmed, Deaths, Lastupdatedtime, Recovered, State;



            public StateModel(String active, String confirmed, String deaths, String lastupdatedtime, String recovered, String state) {
                Active = active;
                Confirmed = confirmed;
                Deaths = deaths;
                Lastupdatedtime = lastupdatedtime;
                Recovered = recovered;
                State = state;
            }

            public String getActive() {
                return Active;
            }

            public String getConfirmed() {
                return Confirmed;
            }

            public String getDeaths() {
                return Deaths;
            }

            public String getLastupdatedtime() {
                return Lastupdatedtime;
            }

            public String getRecovered() {
                return Recovered;
            }

            public String getState() {
                return State;
            }
//            }

            public void setActive(String active) {
                Active = active;
            }

            public void setConfirmed(String confirmed) {
                Confirmed = confirmed;
            }

            public void setDeaths(String deaths) {
                Deaths = deaths;
            }

            public void setLastupdatedtime(String lastupdatedtime) {
                Lastupdatedtime = lastupdatedtime;
            }

            public void setRecovered(String recovered) {
                Recovered = recovered;
            }

            public void setState(String state) {
                State = state;
            }
        }