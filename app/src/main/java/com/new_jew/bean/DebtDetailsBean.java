package com.new_jew.bean;

/**
 * Created by zhangpei on 2016/11/22.
 */
public class DebtDetailsBean {
    private String id,loan_amount,loan_compensatory_amount,loan_overdue_amount,loan_default_amount,
            loan_term,loan_start_overdue_time,vehicle_status,vehicle_color,vehicle_brand_name,
            vehicle_type_name,vehicle_style_name,vehicle_plate_year,vehicle_plate_month,vehicle_ownership,
            vehicle_ownership_name,vehicle_has_gps,vehicle_has_key,vehicle_has_illegal,vehicle_illegal_day,
            vehicle_illegal_details,vehicle_possible_address,demands_of_collection,collecting_days,
            get_level,loan_type,has_historical_collection_record,historical_collection_record,collection_commission,vehicle_plate_number,
            vehicle_engine_number,vehicle_frame_number,normal_key,collecting_day;

    @Override
    public String toString() {
        return "DebtDetailsBean{" +
                "id='" + id + '\'' +
                ", loan_amount='" + loan_amount + '\'' +
                ", loan_compensatory_amount='" + loan_compensatory_amount + '\'' +
                ", loan_overdue_amount='" + loan_overdue_amount + '\'' +
                ", loan_default_amount='" + loan_default_amount + '\'' +
                ", loan_term='" + loan_term + '\'' +
                ", loan_start_overdue_time='" + loan_start_overdue_time + '\'' +
                ", vehicle_status='" + vehicle_status + '\'' +
                ", vehicle_color='" + vehicle_color + '\'' +
                ", vehicle_brand_name='" + vehicle_brand_name + '\'' +
                ", vehicle_type_name='" + vehicle_type_name + '\'' +
                ", vehicle_style_name='" + vehicle_style_name + '\'' +
                ", vehicle_plate_year='" + vehicle_plate_year + '\'' +
                ", vehicle_plate_month='" + vehicle_plate_month + '\'' +
                ", vehicle_ownership='" + vehicle_ownership + '\'' +
                ", vehicle_ownership_name='" + vehicle_ownership_name + '\'' +
                ", vehicle_has_gps='" + vehicle_has_gps + '\'' +
                ", vehicle_has_key='" + vehicle_has_key + '\'' +
                ", vehicle_has_illegal='" + vehicle_has_illegal + '\'' +
                ", vehicle_illegal_day='" + vehicle_illegal_day + '\'' +
                ", vehicle_illegal_details='" + vehicle_illegal_details + '\'' +
                ", vehicle_possible_address='" + vehicle_possible_address + '\'' +
                ", demands_of_collection='" + demands_of_collection + '\'' +
                ", collecting_days='" + collecting_days + '\'' +
                ", get_level='" + get_level + '\'' +
                ", loan_type='" + loan_type + '\'' +
                ", has_historical_collection_record='" + has_historical_collection_record + '\'' +
                ", historical_collection_record='" + historical_collection_record + '\'' +
                ", collection_commission='" + collection_commission + '\'' +
                ", vehicle_plate_number='" + vehicle_plate_number + '\'' +
                ", vehicle_engine_number='" + vehicle_engine_number + '\'' +
                ", vehicle_frame_number='" + vehicle_frame_number + '\'' +
                ", normal_key='" + normal_key + '\'' +
                ", collecting_day='" + collecting_day + '\'' +
                '}';
    }

    public String getCollecting_day() {
        return collecting_day;
    }

    public void setCollecting_day(String collecting_day) {
        this.collecting_day = collecting_day;
    }

    public String getGet_level() {
        return get_level;
    }

    public void setGet_level(String get_level) {
        this.get_level = get_level;
    }

    public String getCollecting_days() {
        return collecting_days;
    }

    public void setCollecting_days(String collecting_days) {
        this.collecting_days = collecting_days;
    }

    public String getCollection_commission() {
        return collection_commission;
    }

    public void setCollection_commission(String collection_commission) {
        this.collection_commission = collection_commission;
    }

    public String getDemands_of_collection() {
        return demands_of_collection;
    }

    public void setDemands_of_collection(String demands_of_collection) {
        this.demands_of_collection = demands_of_collection;
    }

    public String getHas_historical_collection_record() {
        return has_historical_collection_record;
    }

    public void setHas_historical_collection_record(String has_historical_collection_record) {
        this.has_historical_collection_record = has_historical_collection_record;
    }

    public String getHistorical_collection_record() {
        return historical_collection_record;
    }

    public void setHistorical_collection_record(String historical_collection_record) {
        this.historical_collection_record = historical_collection_record;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoan_amount() {
        return loan_amount;
    }

    public void setLoan_amount(String loan_amount) {
        this.loan_amount = loan_amount;
    }

    public String getLoan_compensatory_amount() {
        return loan_compensatory_amount;
    }

    public void setLoan_compensatory_amount(String loan_compensatory_amount) {
        this.loan_compensatory_amount = loan_compensatory_amount;
    }

    public String getLoan_default_amount() {
        return loan_default_amount;
    }

    public void setLoan_default_amount(String loan_default_amount) {
        this.loan_default_amount = loan_default_amount;
    }

    public String getLoan_overdue_amount() {
        return loan_overdue_amount;
    }

    public void setLoan_overdue_amount(String loan_overdue_amount) {
        this.loan_overdue_amount = loan_overdue_amount;
    }

    public String getLoan_start_overdue_time() {
        return loan_start_overdue_time;
    }

    public void setLoan_start_overdue_time(String loan_start_overdue_time) {
        this.loan_start_overdue_time = loan_start_overdue_time;
    }

    public String getLoan_term() {
        return loan_term;
    }

    public void setLoan_term(String loan_term) {
        this.loan_term = loan_term;
    }

    public String getLoan_type() {
        return loan_type;
    }

    public void setLoan_type(String loan_type) {
        this.loan_type = loan_type;
    }

    public String getNormal_key() {
        return normal_key;
    }

    public void setNormal_key(String normal_key) {
        this.normal_key = normal_key;
    }

    public String getVehicle_brand_name() {
        return vehicle_brand_name;
    }

    public void setVehicle_brand_name(String vehicle_brand_name) {
        this.vehicle_brand_name = vehicle_brand_name;
    }

    public String getVehicle_color() {
        return vehicle_color;
    }

    public void setVehicle_color(String vehicle_color) {
        this.vehicle_color = vehicle_color;
    }

    public String getVehicle_engine_number() {
        return vehicle_engine_number;
    }

    public void setVehicle_engine_number(String vehicle_engine_number) {
        this.vehicle_engine_number = vehicle_engine_number;
    }

    public String getVehicle_frame_number() {
        return vehicle_frame_number;
    }

    public void setVehicle_frame_number(String vehicle_frame_number) {
        this.vehicle_frame_number = vehicle_frame_number;
    }

    public String getVehicle_has_gps() {
        return vehicle_has_gps;
    }

    public void setVehicle_has_gps(String vehicle_has_gps) {
        this.vehicle_has_gps = vehicle_has_gps;
    }

    public String getVehicle_has_illegal() {
        return vehicle_has_illegal;
    }

    public void setVehicle_has_illegal(String vehicle_has_illegal) {
        this.vehicle_has_illegal = vehicle_has_illegal;
    }

    public String getVehicle_has_key() {
        return vehicle_has_key;
    }

    public void setVehicle_has_key(String vehicle_has_key) {
        this.vehicle_has_key = vehicle_has_key;
    }

    public String getVehicle_illegal_day() {
        return vehicle_illegal_day;
    }

    public void setVehicle_illegal_day(String vehicle_illegal_day) {
        this.vehicle_illegal_day = vehicle_illegal_day;
    }

    public String getVehicle_illegal_details() {
        return vehicle_illegal_details;
    }

    public void setVehicle_illegal_details(String vehicle_illegal_details) {
        this.vehicle_illegal_details = vehicle_illegal_details;
    }

    public String getVehicle_ownership() {
        return vehicle_ownership;
    }

    public void setVehicle_ownership(String vehicle_ownership) {
        this.vehicle_ownership = vehicle_ownership;
    }

    public String getVehicle_ownership_name() {
        return vehicle_ownership_name;
    }

    public void setVehicle_ownership_name(String vehicle_ownership_name) {
        this.vehicle_ownership_name = vehicle_ownership_name;
    }

    public String getVehicle_plate_month() {
        return vehicle_plate_month;
    }

    public void setVehicle_plate_month(String vehicle_plate_month) {
        this.vehicle_plate_month = vehicle_plate_month;
    }

    public String getVehicle_plate_number() {
        return vehicle_plate_number;
    }

    public void setVehicle_plate_number(String vehicle_plate_number) {
        this.vehicle_plate_number = vehicle_plate_number;
    }

    public String getVehicle_plate_year() {
        return vehicle_plate_year;
    }

    public void setVehicle_plate_year(String vehicle_plate_year) {
        this.vehicle_plate_year = vehicle_plate_year;
    }

    public String getVehicle_possible_address() {
        return vehicle_possible_address;
    }

    public void setVehicle_possible_address(String vehicle_possible_address) {
        this.vehicle_possible_address = vehicle_possible_address;
    }

    public String getVehicle_status() {
        return vehicle_status;
    }

    public void setVehicle_status(String vehicle_status) {
        this.vehicle_status = vehicle_status;
    }

    public String getVehicle_style_name() {
        return vehicle_style_name;
    }

    public void setVehicle_style_name(String vehicle_style_name) {
        this.vehicle_style_name = vehicle_style_name;
    }

    public String getVehicle_type_name() {
        return vehicle_type_name;
    }

    public void setVehicle_type_name(String vehicle_type_name) {
        this.vehicle_type_name = vehicle_type_name;
    }
}
