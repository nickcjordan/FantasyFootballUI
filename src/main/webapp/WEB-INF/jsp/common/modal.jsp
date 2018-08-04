<%-- <div class="modal fade" id="${player.id}playerModal" tabindex="-1" role="dialog" aria-labelledby="${player.id}playerModalTitle" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h1 class="modal-title" id="${player.id}playerModalTitle"><strong>${player.playerName}</strong>&nbsp;&nbsp;<small>${player.pos}</small></h1>
			</div>
			<div class="modal-body">
			
			<ul class="list-group">
			  	<li class="list-group-item"><h3 class="center" >	
					<strong>Tier: </strong>&nbsp;&nbsp;${player.tier}&nbsp;&nbsp;&nbsp;&nbsp;
					<strong>Rank:</strong>&nbsp;&nbsp;${player.rank}&nbsp;&nbsp; &nbsp;&nbsp;
					<strong>Adp:</strong>&nbsp;&nbsp;${player.adp}&nbsp;&nbsp; &nbsp;&nbsp;
					<strong>Vs. Adp:</strong>&nbsp;&nbsp;${player.versus}&nbsp;&nbsp;&nbsp;&nbsp;
					<strong>Pos Rank:</strong>&nbsp;&nbsp;${player.pos_rank}&nbsp;&nbsp; &nbsp;&nbsp;
			  	</h3></li>
			  	<li class="list-group-item"><h3 class="center" >	
					<strong>Best:</strong>&nbsp;&nbsp;${player.best}&nbsp;&nbsp; &nbsp;&nbsp;
					<strong>Avg:</strong>&nbsp;&nbsp;${player.avg}&nbsp;&nbsp; &nbsp;&nbsp;
					<strong>Worst:</strong>&nbsp;&nbsp;${player.worst}&nbsp;&nbsp; &nbsp;&nbsp;
					<strong>Team: </strong>&nbsp;&nbsp;${player.teamName}&nbsp;&nbsp;&nbsp;&nbsp;
					<strong>Bye: </strong>&nbsp;&nbsp;${player.bye}&nbsp;&nbsp;&nbsp;&nbsp;
			  	</h3></li>
			  	<li class="list-group-item"><h4 class="center" >	<strong>Backups: </strong>&nbsp;&nbsp;${player.handcuffs}</h4></li>
			</ul>
			
				<h3><strong>Player Notes:</strong></h3>
				<h4 class="lead">${player.notes}</h4>
			</div>
			<div class="modal-footer">
				<form action="/pickPlayer" method="post">
			        <button type="submit" value="${player.id}" name="playerId" class="btn btn-default">Pick Player</button>
		      </form>
			</div>
		</div>
	</div>
</div>
 --%>
 
 <div class="modal fade" id="${player.id}playerModal" tabindex="-1" role="dialog" aria-labelledby="${player.id}playerModalTitle" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<form action="/pickPlayer" method="post">
				<h1 class="modal-title" id="${player.id}playerModalTitle"><strong>${player.playerName}</strong><span class="modal-spacer"><small>${player.pos}</small></span>
			        <button type="submit" value="${player.id}" name="playerId" class="btn btn-default modal-pick-button">Pick Player</button>
		      </h1></form>
			</div>
			<div class="modal-body col-sm-12">
				<div class="col-sm-10">
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
					<h3><strong>Player Notes:</strong></h3>
					<h4 class="modal-notes">${player.notes}</h4>
				</div>
				<div class="col-sm-2">
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
			<div class="modal-footer">
				
			</div>
		</div>
	</div>
</div>
 
