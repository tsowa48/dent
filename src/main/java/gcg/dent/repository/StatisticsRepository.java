package gcg.dent.repository;

import gcg.dent.dto.Stat;
import io.micronaut.transaction.annotation.ReadOnly;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class StatisticsRepository {

    @Inject
    EntityManager entityManager;

    @Transactional
    @ReadOnly
    public List<Stat> getFirst(int day, int month, int year) {
        Object[] raw = (Object[]) entityManager
                .createNativeQuery(
                        "select (select count(S.cid)::::int4 as \"day\" from slot S where S.cid is not null and extract(year from S.\"date\") = (:year) and extract(month from S.\"date\") = (:month) and extract(day from S.\"date\") = (:day))," +
                                "(select count(S.cid)::::int4 as \"month\" from slot S where S.cid is not null and extract(year from S.\"date\") = (:year) and extract(month from S.\"date\") = (:month))," +
                                "(select count(S.cid)::::int4 as \"year\" from slot S where S.cid is not null and extract(year from S.\"date\") = (:year))")
                .setParameter("day", day)
                .setParameter("month", month)
                .setParameter("year", year)
                .getSingleResult();
        Stat stat = new Stat();
        stat.setDay((int) raw[0]);
        stat.setMonth((int) raw[1]);
        stat.setYear((int) raw[2]);
        List<Stat> ret = new ArrayList<>();
        ret.add(stat);
        return ret;
    }

    @Transactional
    @ReadOnly
    public List<Stat> getSecond(int day, int month, int year) {
        List<Object[]> raw = (List<Object[]>) entityManager
                .createNativeQuery(
                        "select (select count(*)::::int from (select S1.cid from slot S1\n" +
                                "where S1.cid is not null and S1.cid not in (select S.cid from slot S where S.date < make_date((:year), (:month), (:day)) and S.cid is not null group by S.cid)\n" +
                                "and S1.date = make_date((:year), (:month), (:day)) group by S1.cid) D) \"day\",\n" +
                                "(select count(*)::::int from (select S1.cid from slot S1\n" +
                                "where S1.cid is not null and S1.cid not in (select S.cid from slot S where S.date < make_date((:year), (:month), 01) and S.cid is not null group by S.cid)\n" +
                                "and S1.date between make_date((:year), (:month), 01) and (make_date((:year), (:month), 01) + interval '1 month' - interval '1 day') group by S1.cid) M) \"month\",\n" +
                                "(select count(*)::::int from (select S1.cid from slot S1\n" +
                                "where S1.cid is not null and S1.cid not in (select S.cid from slot S where S.date < make_date((:year), 01, 01) and S.cid is not null group by S.cid)\n" +
                                "and S1.date between make_date((:year), 01, 01) and make_date((:year), 12, 31) group by S1.cid) Y) \"year\";")
                .setParameter("day", day)
                .setParameter("month", month)
                .setParameter("year", year)
                .getResultList();
        List<Stat> ret = new ArrayList<>();
        raw.forEach(r -> {
            Stat stat = new Stat();
            stat.setDay((int) r[0]);
            stat.setMonth((int) r[1]);
            stat.setYear((int) r[2]);
            ret.add(stat);
        });
        return ret;
    }

    @Transactional
    @ReadOnly
    public List<Stat> getThird(int day, int month, int year) {
        List<Object[]> raw = (List<Object[]>) entityManager
                .createNativeQuery(
                        "select (select count(*)::::int from (select S1.cid from slot S1\n" +
                                "where S1.cid is not null and S1.cid in (select S.cid from slot S where S.date < make_date((:year), (:month), (:day)) and S.cid is not null group by S.cid)\n" +
                                "and S1.date = make_date((:year), (:month), (:day)) group by S1.cid) D) \"day\",\n" +
                                "(select count(*)::::int from (select S1.cid from slot S1\n" +
                                "where S1.cid is not null and S1.cid in (select S.cid from slot S where S.date < make_date((:year), (:month), 01) and S.cid is not null group by S.cid)\n" +
                                "and S1.date between make_date((:year), (:month), 01) and (make_date((:year), (:month), 01) + interval '1 month' - interval '1 day') group by S1.cid) M) \"month\",\n" +
                                "(select count(*)::::int from (select S1.cid from slot S1\n" +
                                "where S1.cid is not null and S1.cid in (select S.cid from slot S where S.date < make_date((:year), 01, 01) and S.cid is not null group by S.cid)\n" +
                                "and S1.date between make_date((:year), 01, 01) and make_date((:year), 12, 31) group by S1.cid) Y) \"year\";")
                .setParameter("day", day)
                .setParameter("month", month)
                .setParameter("year", year)
                .getResultList();
        List<Stat> ret = new ArrayList<>();
        raw.forEach(r -> {
            Stat stat = new Stat();
            stat.setDay((int) r[0]);
            stat.setMonth((int) r[1]);
            stat.setYear((int) r[2]);
            ret.add(stat);
        });
        return ret;
    }

    @Transactional
    @ReadOnly
    public List<Stat> getFourth(int day, int month, int year) {
        List<Object[]> raw = (List<Object[]>) entityManager
                .createNativeQuery(
                        "select T.name,\n" +
                                "(select count(H.cid)::::int from act A left join history H on H.did=A.did where T.id = A.atid and A.date = make_date((:year), (:month), (:day))) \"day\",\n" +
                                "(select count(H.cid)::::int from act A left join history H on H.did=A.did where T.id = A.atid and A.date between make_date((:year), (:month), 01) and (make_date((:year), (:month), 01) + interval '1 month' - interval '1 day')) \"month\",\n" +
                                "(select count(H.cid)::::int from act A left join history H on H.did=A.did where T.id = A.atid and A.date between make_date((:year), 01, 01) and make_date((:year), 12, 31)) \"year\"\n" +
                                "from act_type T\n" +
                                "group by T.id order by T.name")
                .setParameter("day", day)
                .setParameter("month", month)
                .setParameter("year", year)
                .getResultList();
        List<Stat> ret = new ArrayList<>();
        raw.forEach(r -> {
            Stat stat = new Stat();
            stat.setDoctor((String)r[0]);
            stat.setDay((int) r[1]);
            stat.setMonth((int) r[2]);
            stat.setYear((int) r[3]);
            ret.add(stat);
        });
        return ret;
    }

    @Transactional
    @ReadOnly
    public List<Stat> getFifth(int day, int month, int year) {
        List<Object[]> raw = (List<Object[]>) entityManager
                .createNativeQuery(
                        "with \"day\" as (select sum(A1.amount * S.price) \"value\", E.id from act_service A1 inner join service S on A1.sid = S.id inner join act A on A1.aid = A.id inner join employee E on A.doc = E.id\n" +
                                "    where extract(year from A.\"date\") = (:year)\n" +
                                "        and extract(month from A.\"date\") = (:month)\n" +
                                "        and extract(day from A.\"date\") = (:day)\n" +
                                "    group by E.id\n" +
                                "    ), \"month\" as (select sum(A1.amount * S.price) \"value\", E.id from act_service A1 inner join service S on A1.sid = S.id inner join act A on A1.aid = A.id inner join employee E on A.doc = E.id\n" +
                                "    where extract(year from A.\"date\") = (:year)\n" +
                                "        and extract(month from A.\"date\") = (:month)\n" +
                                "    group by E.id\n" +
                                "    ), \"year\" as (select sum(A1.amount * S.price) \"value\", E.id from act_service A1 inner join service S on A1.sid = S.id inner join act A on A1.aid = A.id inner join employee E on A.doc = E.id\n" +
                                "    where extract(year from A.\"date\") = (:year)\n" +
                                "    group by E.id)\n" +
                                "select coalesce(D.\"value\", 0)::::int as \"day\", coalesce(M.\"value\", 0)::::int as \"month\", coalesce(Y.\"value\", 0)::::int as \"year\", E.fio " +
                                "from employee E left join \"day\" as D on D.id=E.id left join \"month\" as M on M.id=E.id left join \"year\" as Y on Y.id=E.id where E.is_disabled = false order by E.fio")
                .setParameter("day", day)
                .setParameter("month", month)
                .setParameter("year", year)
                .getResultList();
        List<Stat> ret = new ArrayList<>();
        raw.forEach(r -> {
            Stat stat = new Stat();
            stat.setDay((int) r[0]);
            stat.setMonth((int) r[1]);
            stat.setYear((int) r[2]);
            stat.setDoctor((String)r[3]);
            ret.add(stat);
        });
        return ret;
    }

    public Map<String, List<Stat>> getAll(int day, int month, int year) {
        Map<String, List<Stat>> stats = new HashMap<>();

        stats.put("first", getFirst(day, month, year));
        stats.put("second", getSecond(day, month, year));
        stats.put("third", getThird(day, month, year));
        stats.put("fourth", getFourth(day, month, year));
        stats.put("fifth", getFifth(day, month, year));

        return stats;
    }
}
