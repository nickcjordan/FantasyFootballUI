<div class="modal fade" id="${player.id}playerModal" tabindex="-1" role="dialog" aria-labelledby="${player.id}playerModalTitle" aria-hidden="true">
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

