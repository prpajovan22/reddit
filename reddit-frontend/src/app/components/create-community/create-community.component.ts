import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Community } from 'src/app/models/Community';
import { CommunityService } from 'src/app/services/communityService/community.service';

@Component({
  selector: 'app-create-community',
  templateUrl: './create-community.component.html',
  styleUrls: ['./create-community.component.css']
})
export class CreateCommunityComponent implements OnInit {

  community_id:number;
  communitys: Community;
  selectedPDF: File | null = null;

  constructor(private route: ActivatedRoute, private router: Router, private communityService: CommunityService) { }

  ngOnInit(): void {
    this.communitys = new Community();
  }
  redirectToCommunitys(){
    this.router.navigate(['/communitys']);
  }

  createCommunity():void{
    const formData = new FormData();
    formData.append('name', this.communitys.name);
    formData.append('description', this.communitys.description);
    if (this.selectedPDF) {
      formData.append('communityPDF', this.selectedPDF);
    }
    
    this.communityService.createCommunity(formData).subscribe(
      data=>{
      console.log(data);
      this.communitys = new Community();
      console.log(this.communitys);
      this.redirectToCommunitys();
    }, 
    error=>console.log(error));
  }
  onSubmit(){
    this.createCommunity();
  }

  onFileChange(event: any): void {
    this.selectedPDF = event.target.files[0]; 
  }

}
