package com.sesac.climb_mates

import com.sesac.climb_mates.data.campus.CampusClass
import com.sesac.climb_mates.service.CampusService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CampusApplicationTest(
    @Autowired private val campusService: CampusService
) {
    val campusList = mutableListOf("성북캠퍼스", "강북캠퍼스", "도봉캠퍼스", "성동캠퍼스")
    val campusLoc = mutableListOf("서울 성북구 오패산로3길 12 2층","서울 강북구 삼양로 595 솔밭공원역","서울 도봉구 마들로13길 61 씨드큐브 창동 7층","서울 성동구 자동차시장1길 64 청년취업사관학교 성동캠퍼스")

    val seongbukClassList = mutableListOf("헬스케어 서비스 기획 데이터 분석", "All-RA-IT")
    val kangbukClassList = mutableListOf("팀스파르타 데이터분석", "Huggingface 활용 인공지능/LLM", "AI기반 컨텐츠 특화 브랜드 마케터")
    val dobongClassList = mutableListOf("웹툰 제작사", "디지털 전환 주도 SW.AI교육 전문가 취업스쿨")
    val seongdongClassList = mutableListOf("CHAT GPT활용 디지털마케터", "전Z전능 데이터 분석가 양성")

    @Test
    fun createCampus(){
        for(i:Int in campusList.indices){
            println(campusService.createCampus(campusList[i], campusLoc[i]))
        }
    }

    @Test
    fun createClass(){
        for(i:Int in seongbukClassList.indices){
            println(campusService.createCampusClass(
                CampusClass(
                    campus = campusService.getCampusByName("성북캠퍼스").get(),
                    name = seongbukClassList[i],
                )
            ))
        }
    }
}