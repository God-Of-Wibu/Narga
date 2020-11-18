package com.godofwibu.narga.dto;

import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class IssueJsonData {
	int id;
	Time time;
}
