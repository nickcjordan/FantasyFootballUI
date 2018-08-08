<div class="modal fade player-modal" id="${player.id}playerModal" tabindex="-1" role="dialog" aria-labelledby="${player.id}playerModalTitle" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<form action="/pickPlayer" method="post">
				<h1 class="modal-title" id="${player.id}playerModalTitle"><strong>${player.playerName}</strong><span class="modal-spacer"><small>${player.pos}</small></span>
			        <button type="submit" value="${player.id}" name="playerId" class="btn btn-default modal-pick-button">Pick Player</button>
		      </h1></form>
			</div>
			<div class="modal-body col-sm-12">
				<div class="row">
					<div class="col-sm-9">
						<div class="row">
							<div class="col-sm-4 no-pad">
								<div class="player-modal-pic-wrapper">
								
								
									<img src="${player.picLocation}" class="img-fluid img-thumbnail" alt="No Photo Available">
								</div>
							</div>
							<div class="col-sm-8 no-pad">
							  	<ul class="list-group">
									<li class="list-group-item modal-list-item"><h3><strong>Team: </strong><span class="modal-spacer">${player.teamName}</span></h3></li>
							  		<li class="list-group-item modal-list-item"><h4><strong>Backups: </strong><span class="modal-spacer">${player.handcuffs}</span></h4></li>
									<li class="list-group-item modal-list-item"><h3><strong>Bye: </strong><span class="modal-spacer">${player.bye}</span></h3></li>
									<li class="list-group-item modal-list-item"><h3><strong>O-Line: </strong>
										<span class="modal-spacer">Rank:<span class="modal-spacer">${player.getOline_rank()}</span></span>
										<span class="modal-spacer">Run:<span class="modal-spacer">${player.getOline_runScore()}</span></span>
										<span class="modal-spacer">Pass:<span class="modal-spacer">${player.getOline_passScore()}</span></span>
										<span class="modal-spacer">Avg:<span class="modal-spacer">${player.getOline_avgScore()}</span></span>
									</h3></li>
									<li class="list-group-item modal-list-item"><h3><strong>Targets: </strong>
										<span class="modal-spacer">Avg:<span class="modal-spacer">${player.getAvgTargets()}</span></span>
										<span class="modal-spacer">Total:<span class="modal-spacer">${player.getTotalTargets()}</span></span>
									</h3></li>
								</ul>
							</div>
						</div>
					</div>
					<div class="col-sm-3">
						<ul class="list-group">
					  		<li class="list-group-item modal-list-item"><h3><strong>Tier: </strong><span class="modal-spacer modal-stat">${player.tier}</span></h3></li>
							<li class="list-group-item modal-list-item"><h3><strong>Rank:</strong><span class="modal-spacer modal-stat">${player.rank}</span> </h3></li>
							<li class="list-group-item modal-list-item"><h3><strong>Adp:</strong><span class="modal-spacer modal-stat">${player.adp}</span> </h3></li>
							<li class="list-group-item modal-list-item"><h3><strong>Vs. Adp:</strong><span class="modal-spacer modal-stat">${player.versus}</span></h3></li>
							<li class="list-group-item modal-list-item"><h3><strong>Pos Rank:</strong><span class="modal-spacer modal-stat">${player.pos_rank}</span> </h3></li>
							<li class="list-group-item modal-list-item"><h3><strong>Best:</strong><span class="modal-spacer modal-stat">${player.best}</span></h3></li>
							<li class="list-group-item modal-list-item"><h3><strong>Avg:</strong><span class="modal-spacer modal-stat">${player.avg}</span></h3></li>
							<li class="list-group-item modal-list-item"><h3><strong>Worst:</strong><span class="modal-spacer modal-stat">${player.worst}</span></h3></li>
						</ul>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<h3><strong>Player Notes:</strong></h3>
						<h4 class="modal-notes">${player.notes}</h4>
					</div>
				</div>
			</div>
			<div class="modal-footer"></div> <!-- container messes up without it -->
		</div>
	</div>
</div>
 
