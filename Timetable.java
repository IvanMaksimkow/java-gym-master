package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable
            = new HashMap<>();

    public Timetable() {
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        if (!timetable.containsKey(day)) {
            timetable.put(day, new TreeMap<>());
        }

        TreeMap<TimeOfDay, List<TrainingSession>> dayMap = timetable.get(day);

        if (!dayMap.containsKey(time)) {
            dayMap.put(time, new ArrayList<>());
        }

        dayMap.get(time).add(trainingSession);
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        if (!timetable.containsKey(dayOfWeek)) {
            return new ArrayList<>();
        }

        List<TrainingSession> result = new ArrayList<>();
        TreeMap<TimeOfDay, List<TrainingSession>> dayMap = timetable.get(dayOfWeek);

        for (TimeOfDay time : dayMap.navigableKeySet()) {
            result.addAll(dayMap.get(time));
        }

        return result;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        if (!timetable.containsKey(dayOfWeek)) {
            return new ArrayList<>();
        }

        TreeMap<TimeOfDay, List<TrainingSession>> dayMap = timetable.get(dayOfWeek);

        if (!dayMap.containsKey(timeOfDay)) {
            return new ArrayList<>();
        }

        return dayMap.get(timeOfDay);
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        HashMap<Coach, Integer> coachCount = new HashMap<>();

        for (TreeMap<TimeOfDay, List<TrainingSession>> dayMap : timetable.values()) {
            for (List<TrainingSession> sessions : dayMap.values()) {
                for (TrainingSession session : sessions) {
                    Coach coach = session.getCoach();
                    if (!coachCount.containsKey(coach)) {
                        coachCount.put(coach, 0);
                    }
                    coachCount.put(coach, coachCount.get(coach) + 1);
                }
            }
        }

        List<CounterOfTrainings> result = new ArrayList<>();
        for (Map.Entry<Coach, Integer> entry : coachCount.entrySet()) {
            result.add(new CounterOfTrainings(entry.getKey(), entry.getValue()));
        }

        result.sort(new Comparator<CounterOfTrainings>() {
            @Override
            public int compare(CounterOfTrainings a, CounterOfTrainings b) {
                return b.getCount() - a.getCount();
            }
        });

        return result;
    }
}
