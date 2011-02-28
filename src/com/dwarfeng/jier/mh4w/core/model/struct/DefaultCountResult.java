package com.dwarfeng.jier.mh4w.core.model.struct;

import java.util.Map;
import java.util.Objects;

/**
 * 默认统计结果。
 * <p> 统计结果的默认实现。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class DefaultCountResult implements CountResult {

	private final Person person;
	private final double equivalentWorkTime;
	private final double originalWorkTime;
	private final double workticket;
	private final Map<Job, Double> workticketMap;
	private final Map<Job, Double> workticketPercentMap;
	private final double value;
	
	/**
	 * 新实例。
	 * @param person 指定的人员。
	 * @param equivalentWorkTime 指定的等效工时。
	 * @param originalWorkTime 指定的原始工时。
	 * @param workticket 工票总时间。
	 * @param workticketMap 指定的工票工时映射。
	 * @param workticketPercentMap 指定的工票工时百分比映射。
	 * @param value 指定的应得金额。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public DefaultCountResult(Person person, double equivalentWorkTime, double originalWorkTime, double workticket, 
			Map<Job, Double> workticketMap, Map<Job, Double> workticketPercentMap, double value) {
		Objects.requireNonNull(person, "入口参数 person 不能为 null。");
		Objects.requireNonNull(workticketMap, "入口参数 workticketMap 不能为 null。");
		Objects.requireNonNull(workticketPercentMap, "入口参数 workticketPercentMap 不能为 null。");

		this.person = person;
		this.equivalentWorkTime = equivalentWorkTime;
		this.originalWorkTime = originalWorkTime;
		this.workticket = workticket;
		this.workticketMap = workticketMap;
		this.workticketPercentMap = workticketPercentMap;
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.DataWithPerson#getPerson()
	 */
	@Override
	public Person getPerson() {
		return person;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.CountResult#getEquivalentWorkTime()
	 */
	@Override
	public double getEquivalentWorkTime() {
		return equivalentWorkTime;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.CountResult#getOriginalWorkTime()
	 */
	@Override
	public double getOriginalWorkTime() {
		return originalWorkTime;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.CountResult#getWorkticket()
	 */
	@Override
	public double getWorkticket() {
		return workticket;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.CountResult#getWorkticket(com.dwarfeng.jier.mh4w.core.model.struct.Job)
	 */
	@Override
	public double getWorkticket(Job job) {
		return workticketMap.getOrDefault(job, 0.0);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.CountResult#getWorkticketPercent(com.dwarfeng.jier.mh4w.core.model.struct.Job)
	 */
	@Override
	public double getWorkticketPercent(Job job) {
		return workticketPercentMap.getOrDefault(job, 0.0);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.CountResult#getValue()
	 */
	@Override
	public double getValue() {
		return value;
	}

}
