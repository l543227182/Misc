package com.lc.misc.usualTest.extendsData.datamodel;

import com.google.common.collect.Maps;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
public class DaemonBeatData {

    /**
     * 生成influxdb
     *
     * @return
     */
    public Map<String, String> getInfluxTags() {
        Map<String,String> tags = Maps.newHashMap();
        if (Objects.isNull(fields)) {
            return tags;
        }
        tags.put("public_ip", fields.getPublicIp());
        tags.put("private_ip", fields.getPrivateIp());
        tags.put("os_type", fields.getOsType());
        tags.put("collect_instance", StringUtils.join(fields.getCollectInstance(), ","));
        tags.put("project_id", String.valueOf(fields.getProjectId()));
        tags.put("server_id", fields.getServerId());
        return tags;
    }

    public Map<String, String> getInfluxFields() {
        Map fields = Maps.newHashMap();
        // processbeat
        if (Objects.nonNull(allbeats.getProcessbeat())) {
            if (Objects.nonNull(allbeats.getProcessbeat().getProcessbeat())) {
                fields.put("processbeat.memory", allbeats.getProcessbeat().getProcessbeat().getMemory());
                fields.put("processbeat.cpu", allbeats.getProcessbeat().getProcessbeat().getCpu());
                fields.put("processbeat.pid", allbeats.getProcessbeat().getProcessbeat().getPid());
            }
            if (Objects.nonNull(allbeats.getProcessbeat().getFields())) {
                fields.put("processbeat.id", allbeats.getProcessbeat().getFields().getId());
                fields.put("processbeat.beatver", allbeats.getProcessbeat().getFields().getBeatver());
                fields.put("processbeat.ymlver", allbeats.getProcessbeat().getFields().getYmlver());
            }
        }

        // nettoolbeat
        if (Objects.nonNull(allbeats.getNettoolbeat())) {
            if (Objects.nonNull(allbeats.getNettoolbeat().getNettoolbeat())) {
                fields.put("nettoolbeat.memory", allbeats.getNettoolbeat().getNettoolbeat().getMemory());
                fields.put("nettoolbeat.cpu", allbeats.getNettoolbeat().getNettoolbeat().getCpu());
                fields.put("nettoolbeat.pid", allbeats.getNettoolbeat().getNettoolbeat().getPid());
            }
            if (Objects.nonNull(allbeats.getNettoolbeat().getFields())) {
                fields.put("nettoolbeat.id", allbeats.getNettoolbeat().getFields().getId());
                fields.put("nettoolbeat.beatver", allbeats.getNettoolbeat().getFields().getBeatver());
                fields.put("nettoolbeat.ymlver", allbeats.getNettoolbeat().getFields().getYmlver());
            }
        }


        // filebeat
        if (Objects.nonNull(allbeats.getFilebeat())) {
            if(Objects.nonNull(allbeats.getFilebeat().getFilebeat())) {
                fields.put("filebeat.memory", allbeats.getFilebeat().getFilebeat().getMemory());
                fields.put("filebeat.cpu", allbeats.getFilebeat().getFilebeat().getCpu());
                fields.put("filebeat.pid", allbeats.getFilebeat().getFilebeat().getPid());
            }

            if(Objects.nonNull(allbeats.getFilebeat().getFields())) {
                fields.put("filebeat.id", allbeats.getFilebeat().getFields().getId());
                fields.put("filebeat.beatver", allbeats.getFilebeat().getFields().getBeatver());
                fields.put("filebeat.ymlver", allbeats.getFilebeat().getFields().getYmlver());
            }
        }

        // metricbeat
        if (Objects.nonNull(allbeats.getMetricbeat())) {
            if(Objects.nonNull(allbeats.getMetricbeat().getMetricbeat())) {
                fields.put("metricbeat.memory", allbeats.getMetricbeat().getMetricbeat().getMemory());
                fields.put("metricbeat.cpu", allbeats.getMetricbeat().getMetricbeat().getCpu());
                fields.put("metricbeat.pid", allbeats.getMetricbeat().getMetricbeat().getPid());
            }

            if(Objects.nonNull(allbeats.getMetricbeat().getFields())) {
                fields.put("metricbeat.id", allbeats.getMetricbeat().getFields().getId());
                fields.put("metricbeat.beatver", allbeats.getMetricbeat().getFields().getBeatver());
                fields.put("metricbeat.ymlver", allbeats.getMetricbeat().getFields().getYmlver());
            }

        }

        //journalbeat
        if (Objects.nonNull(allbeats.getJournalbeat())) {
            if(Objects.nonNull(allbeats.getJournalbeat().getJournalbeat())) {
                fields.put("journalbeat.memory", allbeats.getJournalbeat().getJournalbeat().getMemory());
                fields.put("journalbeat.cpu", allbeats.getJournalbeat().getJournalbeat().getCpu());
                fields.put("journalbeat.pid", allbeats.getJournalbeat().getJournalbeat().getPid());
            }
            if(Objects.nonNull(allbeats.getJournalbeat().getFields())) {
                fields.put("journalbeat.id", allbeats.getJournalbeat().getFields().getId());
                fields.put("journalbeat.beatver", allbeats.getJournalbeat().getFields().getBeatver());
                fields.put("journalbeat.ymlver", allbeats.getJournalbeat().getFields().getYmlver());
            }
        }
        return fields;
    }

    private Host host;
    private Integer counter;
    private List<String> tags;
    private Agent agent;
    private Ecs ecs;
    private String type;
    private Allbeats allbeats;
    private Daemonbeat daemonbeat;
    private Fields fields;

    @Data
    public static class Metricbeat {

        private boolean enable;
        private String ymlmd5;
        private BeatData metricbeat;
        private BeatFields fields;
    }

    @Data
    public static class Journalbeat {
        private BeatData journalbeat;
        private boolean enable;
        private String ymlmd5;
        private BeatFields fields;
    }

    @Data
    public static class Host {

        private String name;
    }

    @Data
    public static class Nettoolbeat {

        private boolean enable;
        private BeatData nettoolbeat;
        private String ymlmd5;
        private BeatFields fields;
    }

    @Data
    public static class Processbeat {

        private boolean enable;
        private BeatData processbeat;
        private String ymlmd5;
        private BeatFields fields;
    }

    @Data
    public static class Filebeat {

        private boolean enable;
        private BeatData filebeat;
        private String ymlmd5;
        private BeatFields fields;
    }

    @Data
    public static class Fields {
        private String publicIp;
        private String osType;
        private List<Integer> collectInstance;
        private Integer projectId;
        private String serverId;
        private String privateIp;
    }

    @Data
    public static class Ecs {
        private String version;
    }

    @Data
    public static class Daemonbeat {

        private boolean enable;
        private String ymlmd5;
        private BeatData daemonbeat;
        private BeatFields fields;
    }

    @Data
    public static class BeatFields {
        String id;
        String beatver;
        String ymlver;
    }

    @Data
    public static class BeatData {
        String memory;
        String cpu;
        String pid;
    }

    @Data
    public static class Agent {
        private String id;
        private String ephemeralId;
        private String hostname;
        private String version;
        private String type;
        private String name;
    }

    @Data
    public static class Allbeats {

        private Processbeat processbeat;
        private Nettoolbeat nettoolbeat;
        private Filebeat filebeat;
        private Metricbeat metricbeat;
        private Journalbeat journalbeat;
    }

}