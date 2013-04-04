package net.sprauer.sitzplaner.view.Commands;

import java.awt.event.ActionEvent;

import net.sprauer.sitzplaner.EA.Chromosome;
import net.sprauer.sitzplaner.EA.EAFactory;
import net.sprauer.sitzplaner.exceptions.ClassNotLoadedException;
import net.sprauer.sitzplaner.exceptions.ClassRoomToSmallException;
import net.sprauer.sitzplaner.view.ClassRoom;
import net.sprauer.sitzplaner.view.panels.StatisticsPanel;

public class CommandNewSeatingPlan extends AbstractCommand {
	private static final long serialVersionUID = -2671082852870438865L;

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			Chromosome nextGen = EAFactory.nextGeneration();
			ClassRoom.instance().showChromosome(nextGen);
			StatisticsPanel.instance().addFitness(nextGen.getFitness());
		} catch (ClassRoomToSmallException e1) {
			warningBox("Der Klassenraum ist zu klein.", "Sitzplaner - Klasse");
		} catch (ClassNotLoadedException e1) {

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public String getButtonCaption() {
		return "Neu berechnen";
	}

	@Override
	public String getToolTip() {
		return "Neue Sitzordnung aus den gegebenen Parametern berechnen";
	}
}
